package hu.idomsoft.common.validator;

import hu.idomsoft.common.dto.OkmanyDTO;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DocumentNumberValidator implements ConstraintValidator<DocumentNumber, OkmanyDTO> {

  private String fieldName;

  private static final String SZIG_PATTERN = "^([0-9]{6})([A-Za-z]{2})$",
      UTL_PATTERN = "^([A-Za-z]{2})([0-9]{7})$",
      OTHER_PATTERN = "^\\w{1,10}$",
      SZIG_TYPE = "1",
      UTL_TYPE = "2";

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
        message =
            value.getOkmanySzam().matches(SZIG_PATTERN)
                ? ""
                : "meg kell felelnie a " + SZIG_PATTERN + " reguláris kifejezésnek";
        break;
      case UTL_TYPE:
        message =
            value.getOkmanySzam().matches(UTL_PATTERN)
                ? ""
                : "meg kell felelnie a " + UTL_PATTERN + " reguláris kifejezésnek";
        break;
      default:
        message =
            value.getOkmanySzam().matches(UTL_PATTERN)
                ? ""
                : "meg kell felelnie a " + OTHER_PATTERN + " reguláris kifejezésnek";
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
}
