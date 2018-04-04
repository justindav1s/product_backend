package org.jnd.user.repositories;

import org.jnd.microservices.model.Basket;
import org.jnd.microservices.model.Product;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by justin on 13/10/2015.
 */
@Component("BasketRepository")
public class BasketRepository extends HashMap<String, Basket>{

}
