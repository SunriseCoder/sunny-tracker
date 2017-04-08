package tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tracker.entity.IssueStatus;
import tracker.exception.IssueStatusNotFound;

@Service
public interface IssueStatusService {
    public List<IssueStatus> findAll();

    public IssueStatus create(IssueStatus issueStatus);

    public IssueStatus findById(int id) throws IssueStatusNotFound;

    public IssueStatus update(IssueStatus issueStatus) throws IssueStatusNotFound;

    public IssueStatus delete(int id) throws IssueStatusNotFound;
}
