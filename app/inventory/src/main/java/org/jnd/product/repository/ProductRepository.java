package org.jnd.product.repository;

import org.jnd.microservices.model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public interface ProductRepository {

    HashMap<String, Product> getProducts();
    ArrayList<String> getTypes();
}
