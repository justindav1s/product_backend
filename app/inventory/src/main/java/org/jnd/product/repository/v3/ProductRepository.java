package org.jnd.product.repository.v3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jnd.microservices.model.Product;
import org.jnd.microservices.model.ProductType;
import org.jnd.product.repository.ProductCache;
import org.jnd.product.repository.RepositoryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;


@Component("ProductRepository")
@Profile("v3")
public class ProductRepository extends RepositoryBase {

    private Log log = LogFactory.getLog(ProductRepository.class);

    @Autowired
    private ProductCache cache;

    // inject via application.properties
    @Value("${sleep.time.ms:0}")
    private int sleepTime = 0;

    @PostConstruct
    public void init()  {

        log.debug("Setting up repository");
        log.debug("Sleep time (ms) : "+sleepTime);

        getTypes().add(ProductType.FOOD.toString());
        getTypes().add(ProductType.CLOTHES.toString());
        getTypes().add(ProductType.GADGETS.toString());

        getProducts().putAll(cache.getFood());
        getProducts().putAll(cache.getClothes());
        getProducts().putAll(cache.getGadgets());


    }

    public Map<String, Product> getProducts() {

        log.debug("Sleep time (ms) : "+sleepTime);

        return super.getProducts();
    }

    public List<String> getTypes() {

        log.debug("Sleep time (ms) : "+sleepTime);

        return super.getTypes();
    }

}
