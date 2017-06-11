package tracker.service;

import org.springframework.stereotype.Service;

import tracker.entity.IssueStatistic;

@Service
public interface IssueStatisticService {
    void save(IssueStatistic statistic);
}
