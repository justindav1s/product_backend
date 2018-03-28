package org.jnd.basket.proxies;

import com.ib.commercial.model.Product;
import com.ib.commercial.trace.InfoLineBuilder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

/**
 * Created by justin on 13/10/2015.
 */
@Component("ProductRepositoryProxy")
@EnableCircuitBreaker
public class ProductRepositoryProxy {

    private Log log = LogFactory.getLog(ProductRepositoryProxy.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate loadBalancedRestTemplate;

    @HystrixCommand(fallbackMethod = "handleProductNotAvailError")
    public Product getProduct(String id) {

        String[] args = { ProductRepositoryProxy.class.getName(), "getProduct", "basket", id };
        log.debug(InfoLineBuilder.getLine(args, null, null));

        getProductServices();

        ResponseEntity<Product> exchange =
                this.loadBalancedRestTemplate.exchange(
                        "http://product/product/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Product>() {},
                        id);

        Product resp = exchange.getBody();
        log.debug("Product Response : "+resp);

        if (resp == null)
            throw new RuntimeException();

        return resp;
    }

    public Object handleProductNotAvailError(String id) {

        String[] args = { ProductRepositoryProxy.class.getName(), "handleProductNotAvailError", "basket", id };
        log.debug(InfoLineBuilder.getLine(args, null, null));

        log.error("TRIGGERED HYSTRIX CIRCUIT BREAKER");
        return new Product(id, "product not available");
    }


    @HystrixCommand(fallbackMethod = "handleProductServiceError")
    private void getProductServices()   {

        String[] args = { ProductRepositoryProxy.class.getName(), "getProductServices", "basket" };
        log.debug(InfoLineBuilder.getLine(args, null, null));

        List<String> services = discoveryClient.getServices();
        Iterator it = services.iterator();
        while (it.hasNext())   {
            log.debug("Service : "+(String)it.next());
        }
        log.debug("Discovery Client description : " + discoveryClient.description());

        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("product");

        it = serviceInstances.iterator();
        while (it.hasNext())   {
            ServiceInstance instance = (ServiceInstance)it.next();
            log.debug("ServiceInstance : "+instance);
            log.debug("ServiceInstance Host : "+instance.getHost());
            log.debug("ServiceInstance Port : "+instance.getPort());
            log.debug("ServiceInstance Uri : "+instance.getUri());
            log.debug("ServiceInstance id : "+instance.getServiceId());

        }
        this.loadBalancedRestTemplate.getForEntity("http://product/", String.class);
    }

    public Object handleProductServiceError(String id) {

        String[] args = { ProductRepositoryProxy.class.getName(), "handleProductServiceError", "basket", id };
        log.debug(InfoLineBuilder.getLine(args, null, null));

        log.error("TRIGGERED HYSTRIX CIRCUIT BREAKER");
        return new Product(id, "product repository down");
    }
}
