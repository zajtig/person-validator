package hu.idomsoft.common.validator;

import hu.idomsoft.common.service.OkmanytipusDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DocumentTypeValidator implements ConstraintValidator<DocumentType, String> {

    @Autowired
    private OkmanytipusDictionaryService okmanytipusDictionaryService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        String message = null;
        if (!okmanytipusDictionaryService.validateOkmTipus(value)) {
            message = "Nem található okmánytípus! Érkezett:" + value;
        }

        if (!StringUtils.isEmpty(message)) {
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return true;
    }
}
