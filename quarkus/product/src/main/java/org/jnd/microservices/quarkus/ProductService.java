package org.jnd.microservices.quarkus;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jnd.microservices.quarkus.product.model.Product;

import org.jnd.microservices.quarkus.product.repository.ProductRepository;

@Path("/products")
public class ProductService {

    private Log log = LogFactory.getLog(ProductService.class);

    @Inject
    ProductRepository repository;

    @GET
    @Path("")
    @Produces(MediaType.TEXT_PLAIN)
    public String ok() {
        return "OK";
    }
    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "OK";
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Object[] all() {
        Object[] products = (Object[])repository.getProducts().values().toArray();
        log.debug(products);
        return products;
    }

    @GET
    @Path("/types")
    @Produces(MediaType.APPLICATION_JSON)
    public Object[] getTypes() {

        log.info("Product get types");

        Object[] types = (Object[])repository.getTypes().toArray();

        return types;
    }

    @GET
    @Path("/type/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object[] getProductsOfType(@PathParam("type") String type) {

        log.debug("Product get of type :"+type);

        ArrayList<Product> products = new ArrayList<Product>();
        for (Product p : repository.getProducts().values()){
            if (p.getType().toString().equalsIgnoreCase(type))   {
                products.add(p);
            }
        }

        return products.toArray();
    }

    @GET
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product get(@PathParam("productId") Integer productId) {

        log.debug("Product get : "+productId);

        Product product = repository.getProducts().get(Integer.toString(productId));

        return product;
    }

}