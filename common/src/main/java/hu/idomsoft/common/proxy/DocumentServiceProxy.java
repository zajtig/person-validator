package hu.idomsoft.common.proxy;

import hu.idomsoft.common.dto.ProcessDocumentResponse;
import hu.idomsoft.common.dto.SzemelyDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="document-service")
@RibbonClient(name="document-service")
public interface DocumentServiceProxy {

    @PostMapping(value = "/processDocument", consumes = "application/json", produces = "application/json")
    ProcessDocumentResponse processDocument(@RequestBody SzemelyDTO szemelyDTO);

}
