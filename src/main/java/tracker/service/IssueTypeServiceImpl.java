package tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tracker.dao.IssueTypeRepository;
import tracker.entity.IssueType;
import tracker.exception.IssueTypeNotFound;

@Service
public class IssueTypeServiceImpl implements IssueTypeService {
	@Resource
	private IssueTypeRepository repository;
	private Sort sort;

	public IssueTypeServiceImpl() {
		sort = new Sort(new Order(Direction.ASC, "position"));
	}

	@Override
	@Transactional
	public List<IssueType> findAll() {
		return repository.findAll(sort);
	}

	@Override
	@Transactional
	public IssueType create(IssueType issueType) {
		return repository.save(issueType);
	}

	@Override
	@Transactional
	public IssueType findById(int id) throws IssueTypeNotFound {
		IssueType issueType = repository.findOne(id);
		if (issueType == null) {
			throw new IssueTypeNotFound();
		}
		return issueType;
	}

	@Override
	@Transactional(rollbackFor = IssueTypeNotFound.class)
	public IssueType update(IssueType issueType) throws IssueTypeNotFound {
		if (issueType == null || issueType.getId() == null) {
			throw new IssueTypeNotFound();
		}

		IssueType updatedIssueType = repository.findOne(issueType.getId());
		if (updatedIssueType == null) {
			throw new IssueTypeNotFound();
		}

		updatedIssueType.setName(issueType.getName());
		updatedIssueType.setPosition(issueType.getPosition());
		return updatedIssueType;
	}

	@Override
	@Transactional
	public IssueType delete(int id) throws IssueTypeNotFound {
		IssueType deletedIssueType = repository.findOne(id);
		if (deletedIssueType == null) {
			throw new IssueTypeNotFound();
		}
		repository.delete(id);
		return deletedIssueType;
	}
}
