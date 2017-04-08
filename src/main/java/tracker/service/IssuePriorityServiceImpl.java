package tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tracker.dao.IssuePriorityRepository;
import tracker.entity.IssuePriority;
import tracker.exception.IssuePriorityNotFound;

@Component
public class IssuePriorityServiceImpl implements IssuePriorityService {
    @Autowired
    private IssuePriorityRepository repository;
    private Sort sort;

    public IssuePriorityServiceImpl() {
        sort = new Sort(new Order(Direction.ASC, "position"));
    }

    @Override
    @Transactional
    public List<IssuePriority> findAll() {
        return repository.findAll(sort);
    }

    @Override
    @Transactional
    public IssuePriority create(IssuePriority issuePriority) {
        return repository.save(issuePriority);
    }

    @Override
    @Transactional
    public IssuePriority findById(int id) throws IssuePriorityNotFound {
        IssuePriority issuePriority = repository.findOne(id);
        if (issuePriority == null) {
            throw new IssuePriorityNotFound();
        }
        return issuePriority;
    }

    @Override
    @Transactional(rollbackFor = IssuePriorityNotFound.class)
    public IssuePriority update(IssuePriority issuePriority) throws IssuePriorityNotFound {
        if (issuePriority == null || issuePriority.getId() == null) {
            throw new IssuePriorityNotFound();
        }

        IssuePriority updatedIssuePriority = repository.findOne(issuePriority.getId());
        if (updatedIssuePriority == null) {
            throw new IssuePriorityNotFound();
        }

        updatedIssuePriority.setName(issuePriority.getName());
        updatedIssuePriority.setPosition(issuePriority.getPosition());
        updatedIssuePriority.setIssuePosition(issuePriority.getIssuePosition());
        return updatedIssuePriority;
    }

    @Override
    @Transactional
    public IssuePriority delete(int id) throws IssuePriorityNotFound {
        IssuePriority deletedIssuePriority = repository.findOne(id);
        if (deletedIssuePriority == null) {
            throw new IssuePriorityNotFound();
        }
        repository.delete(id);
        return deletedIssuePriority;
    }
}
