package org.jnd.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by justin on 13/10/2015.
 */
public class Basket {


    @Id
    private String id;

    public Basket(String id) {
        this.id = id;
    }

    private List<Product> products = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> items) {
        this.products = items;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(5, 11). // two randomly chosen prime numbers
                append(id).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Basket))
            return false;
        if (obj == this)
            return true;

        Basket rhs = (Basket) obj;
        return new EqualsBuilder().
                append(id, rhs.id).
                isEquals();
    }
}
