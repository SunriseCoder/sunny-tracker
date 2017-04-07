package tracker.dto;

import java.util.List;

import tracker.entity.Project;

public class ProjectIssueTypesDTO {
	private Project project;
	private List<IssueTypeIssuesDTO> issueTypeStructures;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<IssueTypeIssuesDTO> getIssueTypeStructures() {
		return issueTypeStructures;
	}

	public void setIssueTypeStructures(List<IssueTypeIssuesDTO> issueTypeStructures) {
		this.issueTypeStructures = issueTypeStructures;
	}
}
