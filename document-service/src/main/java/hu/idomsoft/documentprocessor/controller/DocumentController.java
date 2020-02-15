package hu.idomsoft.documentprocessor.controller;

import hu.idomsoft.common.dto.OkmanyDTO;
import hu.idomsoft.common.dto.ProcessDocumentRequest;
import hu.idomsoft.common.dto.ProcessDocumentResponse;
import hu.idomsoft.documentprocessor.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DocumentController {
  @Autowired private DocumentService documentService;

  @PostMapping(
      value = "/processDocument",
      consumes = "application/json",
      produces = "application/json")
  public ProcessDocumentResponse processDocument(@RequestBody ProcessDocumentRequest request) {
    ProcessDocumentResponse response = new ProcessDocumentResponse();
    List<OkmanyDTO> result = documentService.process(request.getSzemelyDTO().getOkmLista());
    response.setOkmanyDTOList(result);
    return response;
  }
}
