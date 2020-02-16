package hu.idomsoft.common.validator;

import hu.idomsoft.common.service.OkmanytipusDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DocumentTypeValidator implements ConstraintValidator<DocumentType, String> {

  @Autowired private OkmanytipusDictionaryService okmanytipusDictionaryService;

  @Autowired private MessageSource messageSource;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isEmpty(value)) {
      return true;
    }

    context.disableDefaultConstraintViolation();

    String message = null;
    if (!okmanytipusDictionaryService.validateOkmTipus(value)) {
      message =
          messageSource.getMessage(
              "validator.documentType.error",
              new Object[] {value},
              LocaleContextHolder.getLocale());
    }

    if (!StringUtils.isEmpty(message)) {
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
