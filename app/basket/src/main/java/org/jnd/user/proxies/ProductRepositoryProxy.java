package org.jnd.user.proxies;

import org.jnd.microservices.model.Product;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component("ProductRepositoryProxy")
public class ProductRepositoryProxy {

    @Value( "${inventory.host}" )
    String inventory_host;

    private Log log = LogFactory.getLog(ProductRepositoryProxy.class);

    private RestTemplate restTemplate = new RestTemplate();

//    public Product getProduct(String id, HttpHeaders headers) {
//
//        log.debug("ProductRepositoryProxy get Product id : "+id);
//
//
//        ResponseEntity<Product> exchange =
//                this.restTemplate.exchange(
//                        "http://"+inventory_host+"/products/{id}",
//                        HttpMethod.GET,
//                        null,
//                        new ParameterizedTypeReference<Product>() {},
//                        id);
//
//        Product resp = exchange.getBody();
//        log.debug("Product Response : "+resp);
//
//        if (resp == null)
//            throw new RuntimeException();
//
//        return resp;
//    }

//    public Object getAllProducts(HttpHeaders headers) {
//
//        Object resp = restTemplate.getForObject("http://inventory:8080/products/all", Object.class);
//
//        log.debug("Product Response : "+resp);
//
//        if (resp == null)
//            throw new RuntimeException();
//
//        return resp;
//    }



    public ResponseEntity<Product> getProduct(String id, HttpHeaders headers) {

        log.debug("ProductRepositoryProxy get Product id : "+id);

        ResponseEntity<Product> exchange =
                this.restTemplate.exchange(
                        "http://"+inventory_host+"/products/{id}",
                        HttpMethod.GET,
                        new HttpEntity<byte[]>(headers),
                        new ParameterizedTypeReference<Product>() {},
                        id);

        Product resp = exchange.getBody();
        log.debug("Product Response : "+resp);

        if (exchange == null)
            throw new RuntimeException();

        return exchange;
    }

    public ResponseEntity<List> getAllProducts(HttpHeaders headers) {

        ResponseEntity<List> exchange =
                this.restTemplate.exchange(
                        "http://"+inventory_host+"/products/all",
                        HttpMethod.GET,
                        new HttpEntity<byte[]>(headers),
                        new ParameterizedTypeReference<List>() {});

        log.debug("Producta Response : "+exchange.getBody());

        if (exchange == null)
            throw new RuntimeException();

        return exchange;
    }

    public ResponseEntity<List> getProductsofType(String type, HttpHeaders headers) {

        ResponseEntity<List> exchange =
                this.restTemplate.exchange(
                        "http://"+inventory_host+"/products/type/{type}",
                        HttpMethod.GET,
                        new HttpEntity<byte[]>(headers),
                        new ParameterizedTypeReference<List>() {},
                        type);

        log.debug("AFTER B3Headers");
        getB3Headers(exchange.getHeaders());
        log.debug("Product Response : "+exchange.getBody());

        if (exchange == null)
            throw new RuntimeException();

        return exchange;
    }

    public ResponseEntity<List> getProductTypes(HttpHeaders headers) {

        ResponseEntity<List> exchange =
                this.restTemplate.exchange(
                        "http://"+inventory_host+"/products/types",
                        HttpMethod.GET,
                        new HttpEntity<byte[]>(headers),
                        new ParameterizedTypeReference<List>() {});

        log.debug("Product types response : "+exchange.getBody());

        if (exchange == null)
            throw new RuntimeException();

        return exchange;
    }

    public HttpHeaders getB3Headers(HttpHeaders headers)   {
        HttpHeaders b3Headers = new HttpHeaders();

        log.debug("x-request-id : " + headers.get("x-request-id"));
        log.debug("x-b3-traceid : " + headers.get("x-b3-traceid"));
        log.debug("x-b3-spanid : " + headers.get("x-b3-spanid"));
        log.debug("x-b3-parentspanid : " + headers.get("x-b3-parentspanid"));
        log.debug("x-b3-sampled : " + headers.get("x-b3-sampled"));
        log.debug("x-b3-flags : " + headers.get("x-b3-flags"));
        log.debug("x-ot-span-context : " + headers.get("x-ot-span-context"));

        b3Headers.put("x-request-id", headers.get("x-request-id"));
        b3Headers.put("x-b3-traceid", headers.get("x-b3-traceid"));
        b3Headers.put("x-b3-spanid", headers.get("x-b3-spanid"));
        b3Headers.put("x-b3-parentspanid", headers.get("x-b3-parentspanid"));
        b3Headers.put("x-b3-sampled", headers.get("x-b3-sampled"));
        b3Headers.put("x-b3-flags", headers.get("x-b3-flags"));
        b3Headers.put("x-ot-span-context", headers.get("x-ot-span-context"));

        return b3Headers;
    }

}
