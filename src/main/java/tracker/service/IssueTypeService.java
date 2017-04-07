package tracker.service;

import java.util.List;

import tracker.entity.IssueType;
import tracker.exception.IssueTypeNotFound;

public interface IssueTypeService {
	public List<IssueType> findAll();
	public IssueType create(IssueType issueType);
	public IssueType findById(int id) throws IssueTypeNotFound;
	public IssueType update(IssueType issueType) throws IssueTypeNotFound;
	public IssueType delete(int id) throws IssueTypeNotFound;
}
