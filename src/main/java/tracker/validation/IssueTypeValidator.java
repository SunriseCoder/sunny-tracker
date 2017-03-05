package tracker.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import tracker.model.IssueType;

@Component
public class IssueTypeValidator implements Validator {

	@Override
	public boolean supports(Class<?> cl) {
		return IssueType.class.isAssignableFrom(cl);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "issuetype.name.empty");
		ValidationUtils.rejectIfEmpty(errors, "position", "issuetype.position.empty");
		CustomValidation.rejectIfNotNumeric(errors, "position", "issuetype.position.text");
	}
}
