package org.jnd.model;

import org.springframework.data.annotation.Id;

/**
 * Created by justin on 13/10/2015.
 */
public class Order {

    @Id
    private String id;
    private Basket basket = null;
    private Payment payment = null;
    private Boolean confirmed = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
}
