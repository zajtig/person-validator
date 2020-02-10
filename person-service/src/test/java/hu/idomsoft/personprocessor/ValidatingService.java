package hu.idomsoft.personprocessor;

import hu.idomsoft.common.dto.SzemelyDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
class ValidatingService{

    void validateInput(@Valid SzemelyDTO szemelyDTO){
        // do something
    }

}
