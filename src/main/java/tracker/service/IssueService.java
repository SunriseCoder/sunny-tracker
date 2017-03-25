package tracker.service;

import java.util.List;

import tracker.exception.IssueNotFound;
import tracker.model.Issue;

public interface IssueService {
	public List<Issue> findAll();
	public Issue create(Issue issue) throws IssueNotFound;
	public Issue findById(int id) throws IssueNotFound;
	public Issue update(Issue issue) throws IssueNotFound;
	public Issue delete(int id) throws IssueNotFound;
	public List<Issue> findRootIssues(Integer projectId, Integer issueTypeId);
}