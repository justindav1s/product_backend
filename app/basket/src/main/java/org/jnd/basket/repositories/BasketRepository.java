package org.jnd.basket.repositories;

import org.jnd.microservices.model.Product;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by justin on 13/10/2015.
 */
@Component("BasketRepositoryRepository")
public abstract class BasketRepository extends HashMap<String, Product>{

}
