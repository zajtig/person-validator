package hu.idomsoft.documentprocessor.service;

import hu.idomsoft.common.dto.OkmanyDTO;
import hu.idomsoft.common.dto.ValidationError;
import hu.idomsoft.common.service.OkmanytipusDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class DocumentService {

  @Autowired private Validator validator;

  @Autowired private OkmanytipusDictionaryService okmanytipusDictionaryService;

  public List<OkmanyDTO> process(List<OkmanyDTO> okmanyDTOList) {
    for (OkmanyDTO okmanyDTO : okmanyDTOList) {
      List<ValidationError> validationErrors = validate(okmanyDTO);
      okmanyDTO.setValidationErrors(validationErrors);
      fillData(okmanyDTO);
    }

    return okmanyDTOList;
  }

  private void fillData(OkmanyDTO okmanyDTO) {
    if (isLejarDatValid(okmanyDTO)) {
      Boolean ervenyes = okmanyDTO.getLejarDat().after(new Date());
      okmanyDTO.setErvenyes(ervenyes);
    }
  }

  private List<ValidationError> validate(OkmanyDTO okmanyDTO) {
    List<ValidationError> result = new ArrayList<>();
    Set<ConstraintViolation<OkmanyDTO>> errors = validator.validate(okmanyDTO);

    for (ConstraintViolation<OkmanyDTO> error : errors) {
      ValidationError validationError =
          new ValidationError(error.getPropertyPath().toString(), error.getMessage());
      result.add(validationError);
    }
    return result;
  }

  private boolean isLejarDatValid(OkmanyDTO okmanyDTO) {
    for (ValidationError validationError : okmanyDTO.getValidationErrors()) {
      if ("lejarDat".equals(validationError.getField())) {
        return false;
      }
    }
    return true;
  }
}
