package tracker.exception;

@SuppressWarnings("serial")
public class IssueNotFound extends Exception {
	public IssueNotFound() {
		super();
	}

	public IssueNotFound(String message, Throwable cause) {
		super(message, cause);
	}
}
