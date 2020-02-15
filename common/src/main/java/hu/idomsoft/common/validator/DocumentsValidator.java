package hu.idomsoft.common.validator;

import hu.idomsoft.common.dto.OkmanyDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DocumentsValidator implements ConstraintValidator<Documents, List<OkmanyDTO>> {

  @Override
  public boolean isValid(List<OkmanyDTO> okmanyDTOList, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();

    Map<String, OkmanyDTO> documentCountByType = new HashMap<>();
    List<String> messages = new ArrayList<>();

    for (OkmanyDTO okmanyDTO : okmanyDTOList) {
      if (okmanyDTO.isErvenyes()) {
        if (documentCountByType.containsKey(okmanyDTO.getOkmTipus())) {
          messages.add(
              new StringBuilder("Az ")
                  .append(okmanyDTO.getOkmTipus())
                  .append(" okmányból egynél több érvényes érkezett!")
                  .toString());
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
