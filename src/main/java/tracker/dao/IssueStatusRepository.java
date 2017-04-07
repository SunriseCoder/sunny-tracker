package tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tracker.entity.IssueStatus;

public interface IssueStatusRepository extends JpaRepository<IssueStatus, Integer> {

}
