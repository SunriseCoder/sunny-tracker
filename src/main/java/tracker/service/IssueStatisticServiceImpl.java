package tracker.service;

import java.util.Arrays;
import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tracker.dao.IssueStatisticRepository;
import tracker.entity.Issue;
import tracker.entity.IssueStatistic;

@Component
public class IssueStatisticServiceImpl implements IssueStatisticService {
    private static final Logger logger = LogManager.getLogger(IssueStatisticServiceImpl.class.getName());

    @Autowired
    private IssueService issueService;
    @Autowired
    private IssueStatisticRepository repository;

    @Override
    public List<IssueStatistic> findByIssueId(Integer id) {
        return repository.findByIssueId(id);
    }

    @Override
    public IssueStatistic findLastForIssue(Issue issue) {
        return countStatistic(issue, new Date());
    }

    @Override
    @Transactional
    public void save(IssueStatistic statistic) {
        repository.save(statistic);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void logStatistics() {
        logger.info("logStatistic Job executed");
        Date date = new Date();
        List<Issue> issues = issueService.findMonitored();
        logStatistics(issues, date);
    }

    private void logStatistics(List<Issue> issues, Date date) {
        for (Issue issue : issues) {
            logStatistic(issue, date);
        }
    }

    private void logStatistic(Issue issue, Date date) {
        IssueStatistic statistic = countStatistic(issue, date);
        save(statistic);
    }

    private IssueStatistic countStatistic(Issue issue, Date date) {
        int completed = countLines(issue, i -> i.getStatus().isCompleted());
        int total = countLines(issue, i -> true);

        IssueStatistic statistic = new IssueStatistic();
        statistic.setIssue(issue);
        statistic.setDate(date);
        statistic.setCompleted(completed);
        statistic.setTotal(total);
        return statistic;
    }

    private int countLines(Issue issue, Predicate<Issue> predicate) {
        int count = 0;
        if (predicate.test(issue)) {
            String description = issue.getDescription();
            count += (int) Arrays.stream(description.split("\n"))
                    .map(s -> s.replace("\r", "").trim())
                    .filter(s -> !s.isEmpty())
                    .count() + 1; // Issue with empty description has its own weight
        }

        IntSummaryStatistics statistics = issue.getChilds().stream()
                .map(i -> countLines(i, predicate))
                .collect(Collectors.summarizingInt(Integer::intValue));

        count += statistics.getSum();

        return count;
    }
}
