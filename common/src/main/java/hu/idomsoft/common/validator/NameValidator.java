package hu.idomsoft.common.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {

    private static final String DOCTOR_PATTERN = "[dD][rR].";

    private String fieldName;

    @Override
    public void initialize(Name constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        context.disableDefaultConstraintViolation();

        String message = null;
        StringBuilder stringBuilder = new StringBuilder(value);

        if (value.replaceAll(DOCTOR_PATTERN, "").trim().indexOf(" ") == -1) {
            message = "Legalább két névelem szükséges";
        }

        if (!StringUtils.isEmpty(message)) {
            context.buildConstraintViolationWithTemplate(message)/*.addPropertyNode(fieldName)*/.addConstraintViolation();
            return false;
        }
        return true;
    }
}
