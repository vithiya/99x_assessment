package com.visi.optimalprice.service;

import com.visi.optimalprice.model.CartItem;
import com.visi.optimalprice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptimalPriceCalculator implements PriceCalculator{

    @Autowired
    private ProductService productService;

    @Override
    public Double calculatePrice(CartItem item) {
        String productId = item.getProduct_id();
        Product product = productService.getProductById(Long.parseLong(productId));

        int numOfCartons = Math.floorDiv(item.getQuantity(),product.getUnitsPerCarton());
        int remainingUnits = item.getQuantity() % product.getUnitsPerCarton();

        double optimalPrice =0;

        if(numOfCartons >0 ){
            optimalPrice +=(numOfCartons*product.getCartonPrice());
            if(numOfCartons>=3){
                optimalPrice = addDiscount(numOfCartons,product.getCartonPrice(),optimalPrice);
            }
        }

        if(remainingUnits > 0){
            optimalPrice =addCompensation(product.getCartonPrice(),optimalPrice);
        }
        return optimalPrice;
    }
    private double addDiscount(int numOfCartons, double cartonPrice,double price){
        double discount = numOfCartons *(0.1*cartonPrice);
        price -= discount;
        return price;
    }

    private double addCompensation(double cartonPrice,double price){
        double compensation = cartonPrice * 1.3;
        price += compensation;
        return price;
    }
}
