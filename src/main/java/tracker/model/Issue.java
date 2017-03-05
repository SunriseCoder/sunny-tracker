package tracker.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "issues")
public class Issue {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	private Issue parent;
	@ManyToOne(fetch = FetchType.EAGER)
	private IssueType type;
	@ManyToOne(fetch = FetchType.EAGER)
	private IssueStatus status;
	@ManyToOne(fetch = FetchType.EAGER)
	private IssuePriority priority;
	@ManyToOne(fetch = FetchType.EAGER)
	private Project project;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
	private List<Issue> childs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Issue getParent() {
		return parent;
	}

	public void setParent(Issue parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public IssueType getType() {
		return type;
	}

	public void setType(IssueType type) {
		this.type = type;
	}

	public IssueStatus getStatus() {
		return status;
	}

	public void setStatus(IssueStatus status) {
		this.status = status;
	}

	public IssuePriority getPriority() {
		return priority;
	}

	public void setPriority(IssuePriority priority) {
		this.priority = priority;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Issue> getChilds() {
		return childs;
	}

	public void setChilds(List<Issue> childs) {
		this.childs = childs;
	}
}
