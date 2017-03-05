package tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tracker.exception.IssueNotFound;
import tracker.model.Issue;
import tracker.repository.IssueRepository;

@Service
public class IssueServiceImpl implements IssueService {
	@Resource
	private IssueRepository repository;
	private Sort sort;

	public IssueServiceImpl() {
		sort = new Sort(new Order(Direction.ASC, "status.issuePosition"), new Order(Direction.ASC, "priority.issuePosition"));
	}

	@Override
	@Transactional
	public List<Issue> findAll() {
		return repository.findAll(sort);
	}

	@Override
	@Transactional
	public Issue create(Issue issue) throws IssueNotFound {
		inheritParentValues(issue);
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

		Issue currentIssue = repository.findOne(issue.getId());
		if (currentIssue == null) {
			throw new IssueNotFound();
		}

		copyAllFields(currentIssue, issue);
		inheritParentValues(currentIssue);
		return currentIssue;
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
	@Transactional
	public List<Issue> findRootIssues(Integer projectId, Integer issueTypeId) {
		return repository.findByParentAndProjectIdAndTypeId(null, projectId, issueTypeId, sort);
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
		//TODO Take a close look
		currentIssue.setName(newIssue.getName());
		currentIssue.setDescription(newIssue.getDescription());
		currentIssue.setParent(newIssue.getParent());
		currentIssue.setType(newIssue.getType());
		currentIssue.setProject(newIssue.getProject());
		currentIssue.setStatus(newIssue.getStatus());
		currentIssue.setPriority(newIssue.getPriority());
	}
}
