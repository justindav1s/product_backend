package org.jnd.product.repository;

import org.jnd.microservices.model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class RepositoryBase implements ProductRepository{

    private HashMap<String, Product> products = new HashMap<>();
    private ArrayList<String> types = new ArrayList<>();

    public HashMap<String, Product> getProducts() {
        return products;
    }

    public ArrayList<String> getTypes() {
        return types;
    }
}
