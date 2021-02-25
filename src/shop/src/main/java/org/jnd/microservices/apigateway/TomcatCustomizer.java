package org.jnd.microservices.product;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.product.controller.ProductController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.apache.coyote.http11.AbstractHttp11Protocol;

@Configuration
public class TomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

private Log log = LogFactory.getLog(TomcatCustomizer.class);

  @Override
  public void customize(TomcatServletWebServerFactory factory) {

    factory.addConnectorCustomizers(connector -> {
      AbstractHttp11Protocol protocol = (AbstractHttp11Protocol) connector.getProtocolHandler();

      int originMaxKeepAliveRequests = protocol.getMaxKeepAliveRequests();
      protocol.setMaxKeepAliveRequests(-1);
      int originKeepAliveTimeout = protocol.getKeepAliveTimeout();
      protocol.setKeepAliveTimeout(60000);


      log.info("# origin maxKeepAliveRequests "+ originMaxKeepAliveRequests);
      log.info("# custom maxKeepAliveRequests "+protocol.getMaxKeepAliveRequests());
      log.info("# origin keepalive timeout: {} ms"+originKeepAliveTimeout);
      log.info("# keepalive timeout: {} ms"+protocol.getKeepAliveTimeout());
      log.info("# connection timeout: {} ms"+protocol.getConnectionTimeout());
      log.info("# max connections: {}"+protocol.getMaxConnections());
    });
  }
}
