package tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tracker.entity.IssueStatistic;

@Repository
public interface IssueStatisticRepository extends JpaRepository<IssueStatistic, Integer> {

}
