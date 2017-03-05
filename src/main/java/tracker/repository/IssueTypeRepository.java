package tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tracker.model.IssueType;

public interface IssueTypeRepository extends JpaRepository<IssueType, Integer> {

}
