package org.jnd.microservices.product.repository;

import org.jnd.microservices.model.Product;
import org.jnd.microservices.model.ProductType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("ProductCache")
public class ProductCache {

    public Map<String, Product> getSUVs()   {

        HashMap<String, Product> suvs = new HashMap<>();
        suvs.put("1", new Product("1", "mercedes", ProductType.SUV, 35000f));
        suvs.put("2", new Product("2", "toyota", ProductType.SUV, 23000f));
        suvs.put("3", new Product("3", "alfa", ProductType.SUV, 31000f));
        suvs.put("4", new Product("4", "vw", ProductType.SUV, 28000f));


        return suvs;
    }

    public Map<String, Product> getSaloons() {

        HashMap<String, Product> saloons = new HashMap<>();
        saloons.put("5", new Product("5", "bmw", ProductType.SALOON, 39000f));
        saloons.put("6", new Product("6", "jaguar", ProductType.SALOON, 49000f));
        saloons.put("7", new Product("7", "bentley", ProductType.SALOON, 109000f));
        saloons.put("8", new Product("8", "tesla", ProductType.SALOON, 40000f));
        return saloons;
    }

    public Map<String, Product> getHatchbacks() {

        HashMap<String, Product> hatchbacks = new HashMap<>();
        hatchbacks.put("9", new Product("9", "suzuki", ProductType.HATCHBACK, 11000f));
        hatchbacks.put("10", new Product("10", "ford", ProductType.HATCHBACK, 12000f));
        hatchbacks.put("11", new Product("11", "skoda", ProductType.HATCHBACK, 9000f));
        hatchbacks.put("12", new Product("12", "kia", ProductType.HATCHBACK, 8000f));
        return hatchbacks;
    }
}
