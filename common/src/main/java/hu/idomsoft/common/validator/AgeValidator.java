package hu.idomsoft.common.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class AgeValidator implements ConstraintValidator<Age, Date> {

  private int min;
  private int max;

  @Autowired
  private MessageSource messageSource;

  @Override
  public void initialize(Age constraintAnnotation) {
    max = constraintAnnotation.max();
    min = constraintAnnotation.min();
  }

  @Override
  public boolean isValid(Date value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    context.disableDefaultConstraintViolation();

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime param = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

    long diff = ChronoUnit.YEARS.between(param, now);

    String message = null;
    if (diff < min) {
      message = messageSource.getMessage("validator.age.min.error", new Object[]{min, diff}, LocaleContextHolder.getLocale());
    }
    if (diff > max) {
      message = messageSource.getMessage("validator.age.max.error", new Object[]{min, diff}, LocaleContextHolder.getLocale());
    }

    if (!StringUtils.isEmpty(message)) {
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
