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
    public IssuePriority findSelected() throws IssuePriorityNotFound {
        List<IssuePriority> selected = repository.findBySelectedTrue();

        if (selected.size() != 1) {
            throw new IssuePriorityNotFound();
        }

        return selected.get(0);
    }

    @Override
    @Transactional(rollbackFor = IssuePriorityNotFound.class)
    public IssuePriority update(IssuePriority update) throws IssuePriorityNotFound {
        if (update == null || update.getId() == null) {
            throw new IssuePriorityNotFound();
        }

        IssuePriority storedIssuePriority = repository.findOne(update.getId());
        if (storedIssuePriority == null) {
            throw new IssuePriorityNotFound();
        }

        storedIssuePriority.setName(update.getName());
        storedIssuePriority.setPosition(update.getPosition());
        storedIssuePriority.setIssuePosition(update.getIssuePosition());

        // If current status is selected, resetting all other selected statuses
        if (update.isSelected()) {
            List<IssuePriority> selected = repository.findBySelectedTrue();
            selected.forEach(element -> {
                element.setSelected(false);
                repository.save(element);
            });
            storedIssuePriority.setSelected(update.isSelected());
        }

        return storedIssuePriority;
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
