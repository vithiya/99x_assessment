package com.visi.optimalprice.model;

import javax.persistence.*;

@Entity
@Table(name="cart_item")
public class CartItem {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String product_id;

    private double actual_price;

    private double optimal_price;

    private int quantity;

    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    public CartItem() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public double getActual_price() {
        return actual_price;
    }

    public void setActual_price(double actual_price) {
        this.actual_price = actual_price;
    }

    public double getOptimal_price() {
        return optimal_price;
    }

    public void setOptimal_price(double optimal_price) {
        this.optimal_price = optimal_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
