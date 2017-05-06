package tracker.comparator;

import java.util.Comparator;
import java.util.Date;

import tracker.entity.Issue;

public class IssueComparator implements Comparator<Issue> {

    @Override
    public int compare(Issue o1, Issue o2) {
        if (o1.getPosition() != o2.getPosition()) {
            return o1.getPosition().compareTo(o2.getPosition());
        }

        if (o1.getStatus().getIssuePosition() != o2.getStatus().getIssuePosition()) {
            return o1.getStatus().getIssuePosition() - o2.getStatus().getIssuePosition();
        }

        if (o1.getPriority().getIssuePosition() != o2.getPriority().getIssuePosition()) {
            return o1.getPriority().getIssuePosition() - o2.getPriority().getIssuePosition();
        }

        Date o1c = o1.getChanged();
        Date o2c = o2.getChanged();
        long o1cl = o1c == null ? Long.MIN_VALUE : o1c.getTime();
        long o2cl = o2c == null ? Long.MIN_VALUE : o2c.getTime();
        return o1cl < o2cl ? 1 : o1cl == o2cl ? 0 : -1;
    }
}
