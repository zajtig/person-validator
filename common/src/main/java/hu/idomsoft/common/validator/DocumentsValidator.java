package hu.idomsoft.common.validator;

import hu.idomsoft.common.dto.OkmanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DocumentsValidator implements ConstraintValidator<Documents, List<OkmanyDTO>> {

  @Autowired
  private MessageSource messageSource;

  @Override
  public boolean isValid(List<OkmanyDTO> okmanyDTOList, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();

    Map<String, OkmanyDTO> documentCountByType = new HashMap<>();
    List<String> messages = new ArrayList<>();

    for (OkmanyDTO okmanyDTO : okmanyDTOList) {
      if (okmanyDTO.isErvenyes()) {
        if (documentCountByType.containsKey(okmanyDTO.getOkmTipus())) {
          messages.add(messageSource.getMessage("validator.documents.error", new Object[]{okmanyDTO.getOkmTipus()}, LocaleContextHolder.getLocale()));
        } else {
          documentCountByType.put(okmanyDTO.getOkmTipus(), okmanyDTO);
        }
      }
    }

    if (!messages.isEmpty()) {
      context
          .buildConstraintViolationWithTemplate(
              messages.stream().map(String::valueOf).collect(Collectors.joining()))
          .addConstraintViolation();
      return false;
    }

    return true;
  }
}
