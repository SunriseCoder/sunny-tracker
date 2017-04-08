package tracker.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;

public class CustomValidation {
    private static Pattern numericPattern = Pattern.compile("[0-9]+");

    public static void rejectIfNotNumeric(Errors errors, String field, String errorCode) {
        Object value = errors.getFieldValue(field);
        if (value == null) {
            errors.rejectValue(field, errorCode);
        } else {
            Matcher matcher = numericPattern.matcher(value.toString());
            if (!matcher.matches()) {
                errors.rejectValue(field, errorCode);
            }
        }
    }

}
