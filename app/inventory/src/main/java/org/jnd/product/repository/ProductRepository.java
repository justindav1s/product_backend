package org.jnd.product.repository;

import org.jnd.microservices.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProductRepository {

    Map<String, Product> getProducts();
    List<String> getTypes();
}
