package com.visi.optimalprice.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="order")
public class Order {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order")
    private List<CartItem> cartItemList;

    @Column( name = "total_optimal_price")
    private double totalOptimalPrice;

    @Column( name = "total_actual_price")
    private double totalActualPrice;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public double getTotalOptimalPrice() {
        return totalOptimalPrice;
    }

    public void setTotalOptimalPrice(double totalOptimalPrice) {
        this.totalOptimalPrice = totalOptimalPrice;
    }

    public double getTotalActualPrice() {
        return totalActualPrice;
    }

    public void setTotalActualPrice(double totalActualPrice) {
        this.totalActualPrice = totalActualPrice;
    }
}
