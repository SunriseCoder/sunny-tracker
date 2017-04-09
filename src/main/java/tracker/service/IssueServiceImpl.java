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
    public Issue create(Issue issue) throws IssueNotFound {
        inheritParentValues(issue);
        updateChangedTime(issue);
        return repository.save(issue);
    }

    @Override
    @Transactional
    public Issue findById(int id) throws IssueNotFound {
        Issue issue = repository.findOne(id);
        if (issue == null) {
            throw new IssueNotFound();
        }
        return issue;
    }

    @Override
    @Transactional(rollbackFor = IssueNotFound.class)
    public Issue update(Issue issue) throws IssueNotFound {
        if (issue == null || issue.getId() == null) {
            throw new IssueNotFound();
        }

        Issue storedIssue = repository.findOne(issue.getId());
        if (storedIssue == null) {
            throw new IssueNotFound();
        }

        copyAllFields(storedIssue, issue);
        inheritParentValues(storedIssue);
        updateChangedTime(storedIssue);
        return storedIssue;
    }

    @Override
    @Transactional
    public Issue delete(int id) throws IssueNotFound {
        Issue deletedIssue = repository.findOne(id);
        if (deletedIssue == null) {
            throw new IssueNotFound();
        }
        repository.delete(id);
        return deletedIssue;
    }

    @Override
    public List<Issue> findRootIssues(Integer projectId, Integer typeId) {
        return repository.findByParentIsNullAndProjectIdAndTypeIdOrderByNameAsc(projectId, typeId);
    }

    private void inheritParentValues(Issue issue) throws IssueNotFound {
        Issue parent = issue.getParent();
        if (parent != null) {
            try {
                Issue savedParent = findById(parent.getId());
                issue.setType(savedParent.getType());
                issue.setProject(savedParent.getProject());
            } catch (IssueNotFound e) {
                throw new IssueNotFound("Parent Issue with id = " + parent.getId() + " was not found", e);
            }
        }
    }

    private void copyAllFields(Issue currentIssue, Issue newIssue) {
        // TODO Take a close look
        currentIssue.setName(newIssue.getName());
        currentIssue.setDescription(newIssue.getDescription());
        currentIssue.setParent(newIssue.getParent());
        currentIssue.setType(newIssue.getType());
        currentIssue.setProject(newIssue.getProject());
        currentIssue.setStatus(newIssue.getStatus());
        currentIssue.setPriority(newIssue.getPriority());
    }

    private void updateChangedTime(Issue issue) {
        Date time = new Date();
        issue.setChanged(time);
    }
}
