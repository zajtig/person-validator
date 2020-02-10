package hu.idomsoft.personprocessor.service;

import hu.idomsoft.common.dto.SzemelyDTO;
import hu.idomsoft.common.dto.ValidationError;
import hu.idomsoft.common.service.AllampolgDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PersonService {

    @Autowired
    private Validator validator;

    @Autowired
    private AllampolgDictionaryService allampolgDictionaryService;

    public SzemelyDTO process(SzemelyDTO szemelyDTO) {
        List<ValidationError> validationErrors = validate(szemelyDTO);
        szemelyDTO.setValidationErrors(validationErrors);
        fillData(szemelyDTO);

        return szemelyDTO;
    }

    private List<ValidationError> validate(SzemelyDTO szemelyDTO) {
        List<ValidationError> result = new ArrayList<>();
        Set<ConstraintViolation<SzemelyDTO>> errors = validator.validate(szemelyDTO);

        for (ConstraintViolation<SzemelyDTO> error : errors) {
            ValidationError validationError = new ValidationError(error.getPropertyPath().toString(), error.getMessage());
            result.add(validationError);
        }
        return result;
    }

    private boolean isAllampolgValid(SzemelyDTO szemelyDTO) {
        for (ValidationError validationError : szemelyDTO.getValidationErrors()) {
            if ("allampKod".equals(validationError.getField())) {
                return false;
            }
        }
        return true;
    }

    private void fillData(SzemelyDTO szemelyDTO) {
        if (isAllampolgValid(szemelyDTO)) {
            String allampDekod = allampolgDictionaryService.decodeAllampKod(szemelyDTO.getAllampKod());
            szemelyDTO.setAllampDekod(allampDekod);
        }
    }
}