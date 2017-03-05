package tracker.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import tracker.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Integer> {
	List<Issue> findByParentAndProjectIdAndTypeId(Issue parent, Integer projectId, Integer issueTypeId, Sort sort);
}
