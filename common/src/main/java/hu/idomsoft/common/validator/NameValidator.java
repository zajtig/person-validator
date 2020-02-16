package hu.idomsoft.common.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {

  private static final String DOCTOR_PATTERN = "[dD][rR].";

  @Autowired private MessageSource messageSource;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isEmpty(value)) {
      return true;
    }
    context.disableDefaultConstraintViolation();

    String message = null;

    if (value.replaceAll(DOCTOR_PATTERN, "").trim().indexOf(' ') == -1) {
      message =
          messageSource.getMessage("validator.name.error", null, LocaleContextHolder.getLocale());
    }

    if (!StringUtils.isEmpty(message)) {
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }
    return true;
  }
}
