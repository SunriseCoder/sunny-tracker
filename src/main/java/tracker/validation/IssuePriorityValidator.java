package tracker.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import tracker.entity.IssuePriority;

@Component
public class IssuePriorityValidator implements Validator {

	@Override
	public boolean supports(Class<?> cl) {
		return IssuePriority.class.isAssignableFrom(cl);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "issuepriority.name.empty");
		ValidationUtils.rejectIfEmpty(errors, "position", "issuepriority.position.empty");
		CustomValidation.rejectIfNotNumeric(errors, "position", "issuepriority.position.text");
		ValidationUtils.rejectIfEmpty(errors, "issuePosition", "issuepriority.issueposition.empty");
		CustomValidation.rejectIfNotNumeric(errors, "issuePosition", "issuepriority.issueposition.text");
	}
}
