package tracker.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity(name = "issues")
public class Issue {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    // TODO Rewrite it and test to use ZonedDateTime if JPA 2.2 will support it
    private Date changed;

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

    private Integer position;
    private boolean monitored;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
    private List<Issue> childs;

    @Transient
    private IssueStatistic statistic;

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

    public Date getChanged() {
        return changed;
    }

    public void setChanged(Date changed) {
        this.changed = changed;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public boolean isMonitored() {
        return monitored;
    }

    public void setMonitored(boolean monitored) {
        this.monitored = monitored;
    }

    public List<Issue> getChilds() {
        return childs;
    }

    public void setChilds(List<Issue> childs) {
        this.childs = childs;
    }

    public IssueStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(IssueStatistic statistic) {
        this.statistic = statistic;
    }

    @Override
    public String toString() {
        return String.format(
                "Issue{id: %s, name: %s, description: %s, project: %s, type: %s, status: %s, priority: %s, position-by-status: %s, position-by-priority: %s}",
                id, name, description, project != null ? project.getName() : null, type != null ? type.getName() : null,
                status != null ? status.getName() : null, priority != null ? priority.getName() : null,
                status != null ? status.getIssuePosition() : null,
                priority != null ? priority.getIssuePosition() : null);
    }
}
