package org.jnd.microservices.quarkus.product.repository;

import org.jnd.microservices.quarkus.product.model.Product;
import org.jnd.microservices.quarkus.product.model.ProductType;
import org.jnd.microservices.quarkus.product.repository.RepositoryBase;

import org.jboss.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ProductRepositoryImpl extends RepositoryBase {

    private static final Logger log = Logger.getLogger(ProductRepository.class.getName());

    @Inject
    protected ProductCache cache;

    @ConfigProperty(name = "sleep.time.ms")
    protected int sleepTime = 0;

    @ConfigProperty(name = "active.profile")
    protected String profile = null;

    @PostConstruct
    public void init()  {

        log.debug("Setting up repository");
        log.debug("Sleep time (ms) : "+sleepTime);
        log.debug("Spring Profile : "+profile);

        getTypes().add(ProductType.FOOD.toString());
        getTypes().add(ProductType.CLOTHES.toString());
        getTypes().add(ProductType.GADGETS.toString());

        getProducts().putAll(cache.getFood());
        getProducts().putAll(cache.getClothes());
        getProducts().putAll(cache.getGadgets());


    }

    public Map<String, Product> getProducts() {

        log.debug("Sleep time (ms) : "+sleepTime);
        log.debug("Spring Profile : "+profile);
        sleep(sleepTime);
        return super.getProducts();
    }

    public List<String> getTypes() {

        log.debug("Sleep time (ms) : "+sleepTime);
        log.debug("Spring Profile : "+profile);
        sleep(sleepTime);
        return super.getTypes();
    }

}
