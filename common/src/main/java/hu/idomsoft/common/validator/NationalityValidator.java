package hu.idomsoft.common.validator;

import hu.idomsoft.common.service.AllampolgDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NationalityValidator implements ConstraintValidator<Nationality, String> {

  @Autowired private AllampolgDictionaryService allampolgDictionaryService;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isEmpty(value)) {
      return true;
    }

    context.disableDefaultConstraintViolation();

    String message = null;
    if (!allampolgDictionaryService.validateAllampKod(value)) {
      message = "Nem található nemzetiség! Érkezett:" + value;
    }

    if (!StringUtils.isEmpty(message)) {
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
