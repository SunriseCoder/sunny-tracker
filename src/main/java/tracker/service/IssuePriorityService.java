package tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tracker.entity.IssuePriority;
import tracker.exception.IssuePriorityNotFound;

@Service
public interface IssuePriorityService {
    public List<IssuePriority> findAll();

    public IssuePriority create(IssuePriority issuePriority);

    public IssuePriority findById(int id) throws IssuePriorityNotFound;

    public IssuePriority update(IssuePriority issuePriority) throws IssuePriorityNotFound;

    public IssuePriority delete(int id) throws IssuePriorityNotFound;
}
