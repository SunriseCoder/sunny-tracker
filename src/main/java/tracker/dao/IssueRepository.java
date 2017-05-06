package tracker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tracker.entity.Issue;
import tracker.entity.IssueType;
import tracker.entity.Project;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {
    Integer countByParent(Issue parent);
    Integer countByParentAndPosition(Issue parent, Integer position);

    List<Issue> findByParent(Issue parent);
    List<Issue> findByParentAndPosition(Issue parent, Integer newPosition);
    List<Issue> findByParentIsNullAndProjectAndType(Project project, IssueType issueType);
    List<Issue> findByParentIsNullAndProjectAndTypeAndPosition(Project project, IssueType type, Integer newPosition);
    List<Issue> findByParentIsNullAndProjectIdAndTypeIdOrderByNameAsc(Integer projectId, Integer typeId);
}
