package com.visi.optimalprice.model;

import javax.persistence.*;

@Entity
@Table(name="product")
public class Product {

    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "product_name")
    private String productName;

    @Column( name = "carton_price")
    private double cartonPrice;

    @Column( name = "units_per_carton")
    private int unitsPerCarton;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getCartonPrice() {
        return cartonPrice;
    }

    public void setCartonPrice(double cartonPrice) {
        this.cartonPrice = cartonPrice;
    }

    public int getUnitsPerCarton() {
        return unitsPerCarton;
    }

    public void setUnitsPerCarton(int unitsPerCarton) {
        this.unitsPerCarton = unitsPerCarton;
    }
}
