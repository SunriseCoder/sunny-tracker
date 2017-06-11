package tracker.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tracker.comparator.IssueComparator;
import tracker.dao.IssueRepository;
import tracker.dao.IssueTypeRepository;
import tracker.dao.ProjectRepository;
import tracker.entity.Issue;
import tracker.entity.IssueType;
import tracker.entity.Project;
import tracker.exception.IssueNotFound;

@Component
public class IssueServiceImpl implements IssueService {
    @Autowired
    private IssueRepository repository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private IssueTypeRepository issueTypeRepository;

    private Sort sortStatusPriority;

    public IssueServiceImpl() {
        sortStatusPriority = new Sort(new Order(Direction.ASC, "status.issuePosition"),
                new Order(Direction.ASC, "priority.issuePosition"));
    }

    @Override
    @Transactional
    public Issue findById(int id) throws IssueNotFound {
        Issue issue = repository.findOne(id);
        if (issue == null) {
            throw new IssueNotFound(id);
        }
        return issue;
    }

    @Override
    @Transactional
    public List<Issue> findAll() {
        return repository.findAll(sortStatusPriority);
    }

    @Override
    public List<Issue> findRootIssues(Integer projectId, Integer typeId) {
        return repository.findByParentIsNullAndProjectIdAndTypeIdOrderByNameAsc(projectId, typeId);
    }

    @Override
    public List<Issue> findMonitored() {
        return repository.findByMonitoredTrue();
    }

    @Override
    @Transactional(rollbackFor = IssueNotFound.class)
    public Issue update(Issue issue) throws IssueNotFound {
        if (issue == null) {
            throw new IllegalArgumentException("Issue is null");
        }

        if (issue.getParent() != null && issue.getParent().getId() == 0) {
            issue.setParent(null);
        }

        Set<Issue> branchesToUpdatePositions = new LinkedHashSet<>();
        if (issue.getId() != null && issue.getId() != 0) {
            Issue storedIssue = repository.findOne(issue.getId());

            if (storedIssue == null) {
                throw new IssueNotFound(issue.getId());
            }

            checkParentIsNotAChild(storedIssue, issue.getParent());

            branchesToUpdatePositions.addAll(checkSimilarPosition(issue));
            branchesToUpdatePositions.addAll(checkParentChanged(storedIssue, issue));
        }

        inheritParentValues(issue);
        updateChangedTime(issue);
        setPosition(issue);

        issue = repository.save(issue);

        branchesToUpdatePositions.forEach(parent -> rearrangePositions(parent));

        return issue;
    }

    private Set<Issue> checkSimilarPosition(Issue issue) {
        Integer amount = repository.countByParentAndPosition(issue.getParent(), issue.getPosition());

        if (amount > 0) {
            return Collections.singleton(issue.getParent());
        }

        return Collections.emptySet();
    }

    private void rearrangePositions(Issue parent) {
        if (parent != null) {
            List<Issue> issues = repository.findByParent(parent);
            rearrangeIssuePositions(issues);
            return;
        }

        List<Project> projects = projectRepository.findAll();
        List<IssueType> issueTypes = issueTypeRepository.findAll();
        for (Project project : projects) {
            for (IssueType issueType : issueTypes) {
                List<Issue> issues = repository.findByParentIsNullAndProjectAndType(project, issueType);
                rearrangeIssuePositions(issues);
            }
        }
    }

    private void rearrangeIssuePositions(List<Issue> issues) {
        Collections.sort(issues, new IssueComparator());
        for (int i = 0; i < issues.size(); i++) {
            Issue issue = issues.get(i);
            if (issue.getPosition() != i) {
                issue.setPosition(i);
                repository.save(issue);
            }
        }
    }

    @Override
    @Transactional
    public Issue delete(int id) throws IssueNotFound {
        Issue deletedIssue = repository.findOne(id);
        if (deletedIssue == null) {
            throw new IssueNotFound(id);
        }
        repository.delete(id);
        return deletedIssue;
    }

    private void checkParentIsNotAChild(Issue issue, Issue parent) {
        if (parent == null) {
            return;
        }

        if (issue.getId().equals(parent.getId())) {
            throw new IllegalArgumentException("Issue's parent refers to the issue itself");
        }

        checkParentIsNotAChildRecursively(issue, parent);
    }

    private void checkParentIsNotAChildRecursively(Issue issue, Issue parent) {
        if (issue.getChilds() == null) {
            return;
        }

        for (Issue child : issue.getChilds()) {
            if (parent.getId().equals(child.getId())) {
                throw new IllegalArgumentException("Issue's parent refers to one of the its child");
            }
            checkParentIsNotAChildRecursively(child, parent);
        }
    }

    private Set<Issue> checkParentChanged(Issue oldIssue, Issue newIssue) {
        if (equals(oldIssue.getParent(), newIssue.getParent())) {
            return Collections.emptySet();
        }

        Integer position = repository.countByParent(newIssue.getParent());
        newIssue.setPosition(position);

        Set<Issue> toUpdate = new LinkedHashSet<>();
        toUpdate.add(oldIssue.getParent());
        toUpdate.add(newIssue.getParent());
        return toUpdate;
    }

    private boolean equals(Issue issue1, Issue issue2) {
        if (issue1 == null) {
            return issue1 == issue2;
        }

        return issue1.getId().equals(issue2.getId());
    }

    private void inheritParentValues(Issue issue) throws IssueNotFound {
        Issue parent = issue.getParent();
        if (parent != null) {
            try {
                Issue savedParent = findById(parent.getId());
                issue.setType(savedParent.getType());
                issue.setProject(savedParent.getProject());
            } catch (IssueNotFound e) {
                throw new IssueNotFound(parent.getId(), "Parent Issue with ID {0} was not found");
            }
        }
    }

    private void updateChangedTime(Issue issue) {
        Date time = new Date();
        issue.setChanged(time);
    }

    private void setPosition(Issue issue) {
        if (issue.getPosition() != null) {
            return;
        }

        Integer amount;
        if (issue.getParent() == null) {
            amount = repository.countByParentIsNullAndProjectAndType(issue.getProject(), issue.getType());
        } else {
            amount = repository.countByParent(issue.getParent());
        }

        issue.setPosition(amount);
    }

    @Override
    @Transactional
    public void move(Integer issueId, String direction) throws IssueNotFound {
        switch (direction) {
        case "UP":
            moveUp(issueId);
            break;

        case "DOWN":
            moveDown(issueId);
            break;

        default:
            throw new UnsupportedOperationException("Move '" + direction + "'");
        }
    }

    private void moveUp(Integer issueId) throws IssueNotFound {
        Issue issue = findById(issueId);

        Integer newPosition = Math.max(0, issue.getPosition() - 1);

        List<Issue> anotherIssues;
        if (issue.getParent() == null) {
            anotherIssues = repository.findByParentIsNullAndProjectAndTypeAndPosition(issue.getProject(),
                    issue.getType(), newPosition);
        } else {
            anotherIssues = repository.findByParentAndPosition(issue.getParent(), newPosition);
        }
        anotherIssues.forEach(i -> {
            i.setPosition(issue.getPosition());
            repository.save(i);
        });

        issue.setPosition(newPosition);
        repository.save(issue);
    }

    private void moveDown(Integer issueId) throws IssueNotFound {
        Issue issue = findById(issueId);

        Integer amount = repository.countByParent(issue.getParent());
        Integer newPosition = Math.min(amount, issue.getPosition() + 1);

        List<Issue> anotherIssues;
        if (issue.getParent() == null) {
            anotherIssues = repository.findByParentIsNullAndProjectAndTypeAndPosition(issue.getProject(),
                    issue.getType(), newPosition);
        } else {
            anotherIssues = repository.findByParentAndPosition(issue.getParent(), newPosition);
        }
        anotherIssues.forEach(i -> {
            i.setPosition(issue.getPosition());
            repository.save(i);
        });

        issue.setPosition(newPosition);
        repository.save(issue);
    }

    @Override
    public void sortAllIssues() {
        List<Issue> allIssues = repository.findAll();
        Map<Integer, Issue> issueMap = allIssues.stream().collect(Collectors.toMap(Issue::getId, issue -> issue));

        Set<Integer> allParentIds = new HashSet<>();
        allIssues.forEach(issue -> {
            Integer parentId = issue.getParent() == null ? null : issue.getParent().getId();
            allParentIds.add(parentId);
        });

        allParentIds.stream().map(id -> issueMap.get(id)).forEach(this::rearrangePositions);
    }
}
