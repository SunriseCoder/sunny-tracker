package tracker.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import tracker.entity.IssueStatus;

@Component
public class IssueStatusValidator implements Validator {

    @Override
    public boolean supports(Class<?> cl) {
        return IssueStatus.class.isAssignableFrom(cl);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "issuestatus.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "position", "issuestatus.position.empty");
        CustomValidation.rejectIfNotNumeric(errors, "position", "issuestatus.position.text");
        ValidationUtils.rejectIfEmpty(errors, "issuePosition", "issuestatus.issueposition.empty");
        CustomValidation.rejectIfNotNumeric(errors, "issuePosition", "issuestatus.issueposition.text");
    }
}
