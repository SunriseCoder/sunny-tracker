package tracker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tracker.entity.IssueStatus;

@Repository
public interface IssueStatusRepository extends JpaRepository<IssueStatus, Integer> {
    List<IssueStatus> findBySelectedTrue();
}
