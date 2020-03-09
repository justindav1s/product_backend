package org.jnd.microservices.quarkus.product.repository;

import org.jnd.microservices.quarkus.product.model.Product;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RepositoryBase implements ProductRepository {

    private Log log = LogFactory.getLog(RepositoryBase.class);

    private ConcurrentHashMap<String, Product> products = new ConcurrentHashMap<>();
    private ArrayList<String> types = new ArrayList<>();

    public Map<String, Product> getProducts() {
        return products;
    }

    public List<String> getTypes() {
        return types;
    }

    public void sleep(int millis)   {
        try {
            log.debug("Sleeping : "+millis+" ms");
            Thread.sleep(millis);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

}
