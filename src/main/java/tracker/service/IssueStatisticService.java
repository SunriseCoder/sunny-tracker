package tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tracker.entity.Issue;
import tracker.entity.IssueStatistic;

@Service
public interface IssueStatisticService {
    List<IssueStatistic> findByIssueId(Integer id);
    IssueStatistic findLastForIssue(Issue issue);
    void save(IssueStatistic statistic);
}
