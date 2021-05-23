package com.visi.optimalprice.service;

import com.visi.optimalprice.model.CartItem;
import com.visi.optimalprice.model.Product;
import com.visi.optimalprice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActualPriceCalculator implements PriceCalculator{

    @Autowired
    private ProductService productService;

    @Override
    public Double calculatePrice(CartItem item) {
        String productId = item.getProduct_id();
        Product product = productService.getProductById(Long.parseLong(productId));
        double singleUnitPrice = product.getCartonPrice()/product.getUnitsPerCarton();
        double actualPrice = item.getQuantity() *singleUnitPrice;
        return actualPrice;
    }
}
