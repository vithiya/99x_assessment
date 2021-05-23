package com.visi.optimalprice.service;

import com.visi.optimalprice.model.CartItem;

public interface PriceCalculator {
    
    Double calculatePrice(CartItem item);
}
