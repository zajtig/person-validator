package hu.idomsoft.common.proxy;

import hu.idomsoft.common.dto.ProcessDocumentRequest;
import hu.idomsoft.common.dto.ProcessDocumentResponse;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zuul-gateway-service")
@RibbonClient(name = "document-service")
public interface DocumentServiceProxy {

  @PostMapping(
      value = "/document-service/processDocument",
      consumes = "application/json",
      produces = "application/json")
  ProcessDocumentResponse processDocument(
      @RequestBody ProcessDocumentRequest processDocumentRequest);
}
