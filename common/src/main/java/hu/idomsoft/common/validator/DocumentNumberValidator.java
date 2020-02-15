package hu.idomsoft.common.validator;

import hu.idomsoft.common.dto.OkmanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DocumentNumberValidator implements ConstraintValidator<DocumentNumber, OkmanyDTO> {

  private static final String SZIG_PATTERN = "^([0-9]{6})([A-Za-z]{2})$";
  private static final String UTL_PATTERN = "^([A-Za-z]{2})([0-9]{7})$";
  private static final String OTHER_PATTERN = "^\\w{1,10}$";
  private static final String SZIG_TYPE = "1";
  private static final String UTL_TYPE = "2";

  private String fieldName;

  @Autowired
  private MessageSource messageSource;

  @Override
  public void initialize(DocumentNumber constraintAnnotation) {
    fieldName = constraintAnnotation.fieldName();
  }

  @Override
  public boolean isValid(OkmanyDTO value, ConstraintValidatorContext context) {
    if (StringUtils.isEmpty(value.getOkmTipus()) || StringUtils.isEmpty(value.getOkmanySzam())) {
      return true;
    }
    context.disableDefaultConstraintViolation();
    String message = null;
    switch (value.getOkmTipus()) {
      case SZIG_TYPE:
        message = matches(value.getOkmanySzam(), SZIG_PATTERN);
        break;
      case UTL_TYPE:
        message = matches(value.getOkmanySzam(), UTL_PATTERN);
        break;
      default:
        message = matches(value.getOkmanySzam(), OTHER_PATTERN);
    }
    if (!StringUtils.isEmpty(message)) {
      context
          .buildConstraintViolationWithTemplate(message)
          .addPropertyNode(fieldName)
          .addConstraintViolation();
      return false;
    }
    return true;
  }

  private String matches(String value, String pattern) {
    return value.matches(pattern)
                    ? ""
                    : messageSource.getMessage("validator.documentNumber.error", new Object[]{pattern}, LocaleContextHolder.getLocale());
  }
}