package org.jnd.product.repository;

import org.jnd.model.Product;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by justin on 13/10/2015.
 */
@Component("ProductRepository")
public class ProductRepository extends HashMap<String, Product>{

}
