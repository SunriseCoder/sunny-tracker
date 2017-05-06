package tracker.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tracker.dao.IssueRepository;
import tracker.entity.Issue;
import tracker.exception.IssueNotFound;

@Component
public class IssueServiceImpl implements IssueService {
    @Autowired
    private IssueRepository repository;
    private Sort sortStatusPriority;

    public IssueServiceImpl() {
        sortStatusPriority = new Sort(new Order(Direction.ASC, "status.issuePosition"),
                new Order(Direction.ASC, "priority.issuePosition"));
    }

    @Override
    @Transactional
    public List<Issue> findAll() {
        return repository.findAll(sortStatusPriority);
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
    @Transactional(rollbackFor = IssueNotFound.class)
    public Issue update(Issue issue) throws IssueNotFound {
        if (issue == null) {
            throw new IllegalArgumentException("Issue is null");
        }

        if (issue.getParent() != null && issue.getParent().getId() == 0) {
            issue.setParent(null);
        }

        if (issue.getId() != null && issue.getId() != 0) {
            Issue storedIssue = repository.findOne(issue.getId());

            if (storedIssue == null) {
                throw new IssueNotFound(issue.getId());
            }

            checkParentIsNotAChild(storedIssue, issue.getParent());
        }

        inheritParentValues(issue);
        updateChangedTime(issue);

        issue = repository.save(issue);

        return issue;
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

    @Override
    public List<Issue> findRootIssues(Integer projectId, Integer typeId) {
        return repository.findByParentIsNullAndProjectIdAndTypeIdOrderByNameAsc(projectId, typeId);
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

    @Override
    public void move(Integer issueId, String direction) {
        // TODO Auto-generated method stub
    }
}
