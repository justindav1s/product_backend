package com.ib.booking.basket.repositories;

import com.ib.commercial.model.Basket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.HashMap;

/**
 * Created by justin on 13/10/2015.
 */
public interface BasketRepository extends MongoRepository<Basket, String>   {

}
