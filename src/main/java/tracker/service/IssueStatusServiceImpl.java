package tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tracker.dao.IssueStatusRepository;
import tracker.entity.IssueStatus;
import tracker.exception.IssueStatusNotFound;

@Service
public class IssueStatusServiceImpl implements IssueStatusService {
	@Resource
	private IssueStatusRepository repository;
	private Sort sort;

	public IssueStatusServiceImpl() {
		sort = new Sort(new Order(Direction.ASC, "position"));
	}

	@Override
	@Transactional
	public List<IssueStatus> findAll() {
		return repository.findAll(sort);
	}

	@Override
	@Transactional
	public IssueStatus create(IssueStatus issueStatus) {
		return repository.save(issueStatus);
	}

	@Override
	@Transactional
	public IssueStatus findById(int id) throws IssueStatusNotFound {
		IssueStatus issueStatus = repository.findOne(id);
		if (issueStatus == null) {
			throw new IssueStatusNotFound();
		}
		return issueStatus;
	}

	@Override
	@Transactional(rollbackFor = IssueStatusNotFound.class)
	public IssueStatus update(IssueStatus issueStatus) throws IssueStatusNotFound {
		if (issueStatus == null || issueStatus.getId() == null) {
			throw new IssueStatusNotFound();
		}

		IssueStatus updatedIssueStatus = repository.findOne(issueStatus.getId());
		if (updatedIssueStatus == null) {
			throw new IssueStatusNotFound();
		}

		updatedIssueStatus.setName(issueStatus.getName());
		updatedIssueStatus.setPosition(issueStatus.getPosition());
		updatedIssueStatus.setIssuePosition(issueStatus.getIssuePosition());
		return updatedIssueStatus;
	}

	@Override
	@Transactional
	public IssueStatus delete(int id) throws IssueStatusNotFound {
		IssueStatus deletedIssueStatus = repository.findOne(id);
		if (deletedIssueStatus == null) {
			throw new IssueStatusNotFound();
		}
		repository.delete(id);
		return deletedIssueStatus;
	}
}
