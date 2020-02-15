package hu.idomsoft.personprocessor.controller;

import hu.idomsoft.common.dto.*;
import hu.idomsoft.common.proxy.DocumentServiceProxy;
import hu.idomsoft.personprocessor.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

  @Autowired private PersonService personService;

  @Autowired private DocumentServiceProxy documentServiceProxy;

  @PostMapping(
      value = "/processPerson",
      consumes = "application/json",
      produces = "application/json")
  public ProcessPersonResponse processPerson(@RequestBody ProcessPersonRequest request) {
    SzemelyDTO szemelyDTO = request.getSzemelyDTO();
    ProcessDocumentRequest processDocumentRequest = new ProcessDocumentRequest();
    processDocumentRequest.setSzemelyDTO(szemelyDTO);
    ProcessDocumentResponse processPersonResponse =
        documentServiceProxy.processDocument(processDocumentRequest);
    szemelyDTO.setOkmLista(processPersonResponse.getOkmanyDTOList());

    ProcessPersonResponse response = new ProcessPersonResponse();
    SzemelyDTO result = personService.process(szemelyDTO);
    response.setSzemelyDTO(result);
    return response;
  }
}
