package tracker.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import tracker.entity.Project;

@Component
public class ProjectValidator implements Validator {

	@Override
	public boolean supports(Class<?> cl) {
		return Project.class.isAssignableFrom(cl);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "project.name.empty");
		ValidationUtils.rejectIfEmpty(errors, "position", "project.position.empty");
		CustomValidation.rejectIfNotNumeric(errors, "position", "project.position.text");
	}
}
