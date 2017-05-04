package tracker.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "issue_statuses")
public class IssueStatus {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private int position;
    private int issuePosition;
    private Boolean selected;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getIssuePosition() {
        return issuePosition;
    }

    public void setIssuePosition(int issuePosition) {
        this.issuePosition = issuePosition;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return String.format("IssueStatus{id: %s, name: %s, position: %s, issue-position: %s}", id, name, position,
                issuePosition);
    }
}
