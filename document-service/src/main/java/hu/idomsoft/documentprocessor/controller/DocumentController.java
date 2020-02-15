package hu.idomsoft.documentprocessor.controller;

import hu.idomsoft.common.dto.OkmanyDTO;
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
    public ProcessDocumentResponse processDocument(@RequestBody SzemelyDTO szemelyDTO) {
        logger.debug("request: {}", szemelyDTO);
        ProcessDocumentResponse response = new ProcessDocumentResponse();
        List<OkmanyDTO> result = documentService.process(szemelyDTO.getOkmLista());
        response.setOkmanyDTOList(result);
        logger.debug("responset: {}", response);
        return response;
    }
}
