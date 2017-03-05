package tracker.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import tracker.model.Issue;

@Component
public class IssueValidator implements Validator {

	@Override
	public boolean supports(Class<?> cl) {
		return Issue.class.isAssignableFrom(cl);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "issue.name.empty");
		ValidationUtils.rejectIfEmpty(errors, "type", "issue.type.empty");
		ValidationUtils.rejectIfEmpty(errors, "project", "issue.project.empty");
		ValidationUtils.rejectIfEmpty(errors, "status", "issue.status.empty");
		ValidationUtils.rejectIfEmpty(errors, "priority", "issue.priority.empty");
	}
}
