package hu.idomsoft.documentprocessor.controller;

import hu.idomsoft.common.dto.OkmanyDTO;
import hu.idomsoft.common.dto.ProcessDocumentRequest;
import hu.idomsoft.common.dto.ProcessDocumentResponse;
import hu.idomsoft.common.dto.SzemelyDTO;
import hu.idomsoft.documentprocessor.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DocumentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DocumentService documentService;

    @PostMapping(value = "/processDocument", consumes = "application/json", produces = "application/json")
    public ProcessDocumentResponse processDocument(@RequestBody ProcessDocumentRequest request) {
        logger.debug("request: {}", request);
        ProcessDocumentResponse response = new ProcessDocumentResponse();
        List<OkmanyDTO> result = documentService.process(request.getSzemelyDTO().getOkmLista());
        response.setOkmanyDTOList(result);
        logger.debug("responset: {}", response);
        return response;
    }
}
