package hu.idomsoft;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Component
public class ZuulLoggingRequest extends ZuulFilter {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {
    try {
      RequestContext context = RequestContext.getCurrentContext();
      InputStream in = context.getRequest().getInputStream();
      String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
      logger.info("request: {}", body);

    } catch (IOException e) {
      logger.warn("", e);
    }
    return null;
  }
}
