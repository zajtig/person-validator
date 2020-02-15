package hu.idomsoft.common.validator;

import hu.idomsoft.common.service.AllampolgDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NationalityValidator implements ConstraintValidator<Nationality, String> {

  @Autowired
  private AllampolgDictionaryService allampolgDictionaryService;

  @Autowired
  private MessageSource messageSource;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isEmpty(value)) {
      return true;
    }

    context.disableDefaultConstraintViolation();

    String message = null;
    if (!allampolgDictionaryService.validateAllampKod(value)) {
      message = messageSource.getMessage("validator.nationality.error", new Object[] {value}, LocaleContextHolder.getLocale());
    }

    if (!StringUtils.isEmpty(message)) {
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
