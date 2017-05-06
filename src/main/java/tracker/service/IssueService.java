package tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tracker.entity.Issue;
import tracker.exception.IssueNotFound;

@Service
public interface IssueService {
    public List<Issue> findAll();
    public Issue findById(int id) throws IssueNotFound;
    public Issue update(Issue issue) throws IssueNotFound;
    public Issue delete(int id) throws IssueNotFound;
    public List<Issue> findRootIssues(Integer projectId, Integer typeId);
    public void move(Integer issueId, String direction) throws IssueNotFound;
    public void sortAllIssues();
}
