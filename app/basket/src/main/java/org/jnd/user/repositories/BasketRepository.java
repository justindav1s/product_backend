package org.jnd.user.repositories;

import org.jnd.microservices.model.Basket;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by justin on 13/10/2015.
 */
@Component("BasketRepository")
public class BasketRepository {

    private HashMap<Integer, Basket> baskets = new HashMap<Integer, Basket>();
    private HashMap<String, Integer> lookups = new HashMap<String, Integer>();

    public Basket get(Integer basketId)   {
        return baskets.get(basketId);
    }

    public Basket get(String username)   {
        Integer basketId = lookups.get(username);
        return baskets.get(basketId);
    }

    public void add(Basket basket)   {
        lookups.put(basket.getUserId(), basket.getId());
        baskets.put(basket.getId(), basket);
    }

    public void remove(Basket basket)   {
        lookups.remove(basket.getUserId());
        baskets.remove(basket.getId());
    }

    public void clear()   {
        lookups.clear();
        baskets.clear();
    }

    public Set<Map.Entry<Integer, Basket>> entrySet()  {
        return baskets.entrySet();
    }

    public Integer size()   {
        return baskets.size();
    }
}
