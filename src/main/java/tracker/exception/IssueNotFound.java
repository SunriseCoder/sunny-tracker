package tracker.exception;

import java.text.MessageFormat;

@SuppressWarnings("serial")
public class IssueNotFound extends Exception {
    private String messagePattern = "Issue with ID {0} not found";
    private Integer id;

    public IssueNotFound(Integer id) {
        this.id = id;
    }

    public IssueNotFound(Integer id, String messagePattern) {
        this.id = id;
        this.messagePattern = messagePattern;
    }

    @Override
    public String getMessage() {
        String message = MessageFormat.format(messagePattern, id);
        return message;
    }
}
