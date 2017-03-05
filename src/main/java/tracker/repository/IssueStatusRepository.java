package tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tracker.model.IssueStatus;

public interface IssueStatusRepository extends JpaRepository<IssueStatus, Integer> {

}
