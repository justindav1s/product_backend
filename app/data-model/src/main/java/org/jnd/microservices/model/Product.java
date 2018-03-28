package org.jnd.microservices.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by justin on 13/10/2015.
 */
public class Product {

    private String id;
    private String name = null;

    private Product() {}

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(5, 7). // two randomly chosen prime numbers
                append(name).
                append(id).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product))
            return false;
        if (obj == this)
            return true;

        Product rhs = (Product) obj;
        return new EqualsBuilder().
                append(name, rhs.name).
                append(id, rhs.id).
                isEquals();
    }
}
