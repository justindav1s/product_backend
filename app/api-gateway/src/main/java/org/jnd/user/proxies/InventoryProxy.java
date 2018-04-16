package org.jnd.user.proxies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component("InventoryProxy")
public class InventoryProxy {

    private Log log = LogFactory.getLog(InventoryProxy.class);

    @Value( "${inventory.host}" )
    String inventory_host;

    private RestTemplate restTemplate = new RestTemplate();;

    public Product getProduct(String id, HttpHeaders headers) {

        log.debug("ProductRepositoryProxy get Product id : "+id);


        ResponseEntity<Product> exchange =
                this.restTemplate.exchange(
                        "http://"+inventory_host+"/products/{id}",
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

    public List<Product> getAllProducts(HttpHeaders headers) {

        List<Product> products = restTemplate.getForObject("http://"+inventory_host+"/products/all", List.class);

        log.debug("Product Response : "+products);

        if (products == null)
            throw new RuntimeException();

        return products;
    }

    public List<Product> getProductsofType(String type, HttpHeaders headers) {
        List<Product> products = restTemplate.getForObject("http://"+inventory_host+"/products/type/"+type, List.class);
        ResponseEntity<List> response = restTemplate.getForEntity("http://"+inventory_host+"/products/type/"+type, List.class);

        log.debug("AFTER B3Headers");
        getB3Headers(response.getHeaders());
        log.debug("Product Response : "+response.getBody());

        if (products == null)
            throw new RuntimeException();

        return response.getBody();
    }

    public List<String> getProductTypes(HttpHeaders headers) {
        List<String> products = restTemplate.getForObject("http://"+inventory_host+"/products/types", List.class);

        log.debug("Product types response : "+products);

        if (products == null)
            throw new RuntimeException();

        return products;
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
