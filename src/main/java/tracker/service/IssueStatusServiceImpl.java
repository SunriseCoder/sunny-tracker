package tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tracker.dao.IssueStatusRepository;
import tracker.entity.IssueStatus;
import tracker.exception.IssueStatusNotFound;

@Component
public class IssueStatusServiceImpl implements IssueStatusService {
    @Autowired
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
    public IssueStatus findSelected() throws IssueStatusNotFound {
        List<IssueStatus> selected = repository.findBySelectedTrue();

        if (selected.size() != 1) {
            throw new IssueStatusNotFound();
        }

        return selected.get(0);
    }

    @Override
    @Transactional(rollbackFor = IssueStatusNotFound.class)
    public IssueStatus update(IssueStatus update) throws IssueStatusNotFound {
        if (update == null || update.getId() == null) {
            throw new IssueStatusNotFound();
        }

        IssueStatus storedIssueStatus = repository.findOne(update.getId());
        if (storedIssueStatus == null) {
            throw new IssueStatusNotFound();
        }

        storedIssueStatus.setName(update.getName());
        storedIssueStatus.setPosition(update.getPosition());
        storedIssueStatus.setIssuePosition(update.getIssuePosition());

        // If current status is selected, resetting all other selected statuses
        if (update.isSelected()) {
            List<IssueStatus> selected = repository.findBySelectedTrue();
            selected.forEach(element -> {
                element.setSelected(false);
                repository.save(element);
            });
            storedIssueStatus.setSelected(update.isSelected());
        }

        storedIssueStatus.setCompleted(update.isCompleted());

        return storedIssueStatus;
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
