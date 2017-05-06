package tracker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tracker.entity.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {
    Integer countByParent(Issue parent);
    Integer countByParentAndPosition(Issue parent, Integer position);

    List<Issue> findByParent(Issue parent);
    List<Issue> findByParentAndPosition(Issue parent, Integer newPosition);
    List<Issue> findByParentIsNullAndProjectIdAndTypeIdOrderByNameAsc(Integer projectId, Integer typeId);
}
