package tracker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tracker.entity.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {
    List<Issue> findByParentIsNullAndProjectIdAndTypeIdOrderByNameAsc(Integer projectId, Integer typeId);
}
