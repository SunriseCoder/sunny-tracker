package tracker.dto;

import java.util.List;

import tracker.entity.Issue;
import tracker.entity.IssueType;

public class IssueTypeIssuesDTO {
	private IssueType issueType;
	private List<Issue> issues;

	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}
}
