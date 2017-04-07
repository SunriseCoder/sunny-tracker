package tracker.service;

import java.util.List;

import tracker.entity.IssueStatus;
import tracker.exception.IssueStatusNotFound;

public interface IssueStatusService {
	public List<IssueStatus> findAll();
	public IssueStatus create(IssueStatus issueStatus);
	public IssueStatus findById(int id) throws IssueStatusNotFound;
	public IssueStatus update(IssueStatus issueStatus) throws IssueStatusNotFound;
	public IssueStatus delete(int id) throws IssueStatusNotFound;
}
