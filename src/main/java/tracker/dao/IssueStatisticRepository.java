package tracker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tracker.entity.IssueStatistic;

@Repository
public interface IssueStatisticRepository extends JpaRepository<IssueStatistic, Integer> {
    List<IssueStatistic> findByIssueId(Integer id);
}
