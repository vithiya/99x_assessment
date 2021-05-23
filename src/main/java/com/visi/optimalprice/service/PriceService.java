package com.visi.optimalprice.service;

import com.visi.optimalprice.model.Order;
import com.visi.optimalprice.model.ProductPrice;

import java.util.List;
import java.util.Map;

public interface PriceService {
    Map<Integer,Double> getInitialPriceData(long productId);

    Order calculateOptimalPrice(Order order);

    List<ProductPrice> getAllProductPriceList();
}
