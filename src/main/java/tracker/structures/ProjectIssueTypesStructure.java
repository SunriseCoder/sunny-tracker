package tracker.structures;

import java.util.List;

import tracker.model.Project;

public class ProjectIssueTypesStructure {
	private Project project;
	private List<IssueTypeIssuesStructure> issueTypeStructures;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<IssueTypeIssuesStructure> getIssueTypeStructures() {
		return issueTypeStructures;
	}

	public void setIssueTypeStructures(List<IssueTypeIssuesStructure> issueTypeStructures) {
		this.issueTypeStructures = issueTypeStructures;
	}
}
