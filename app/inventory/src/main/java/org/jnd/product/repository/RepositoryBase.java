package org.jnd.product.repository;

import org.jnd.microservices.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryBase implements ProductRepository{

    private HashMap<String, Product> products = new HashMap<>();
    private ArrayList<String> types = new ArrayList<>();

    public Map<String, Product> getProducts() {
        return products;
    }

    public List<String> getTypes() {
        return types;
    }
}
