package com.visi.optimalprice.service;

import com.visi.optimalprice.model.CartItem;
import com.visi.optimalprice.model.Order;
import com.visi.optimalprice.model.Product;
import com.visi.optimalprice.model.ProductPrice;
import com.visi.optimalprice.repository.OrderRepository;
import com.visi.optimalprice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PriceServiceImpl implements PriceService{

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ActualPriceCalculator priceCalculator;

    @Autowired
    private OptimalPriceCalculator optimalPriceCalculator;

    @Override
    public Map<Integer, Double> getInitialPriceData(long productId) {
        Product product =productService.getProductById(productId);
        return calculateInitalPrice(product);
    }

    @Override
    public Order calculateOptimalPrice(Order order) {
        double totalOptimalPrice =0;
        double totalActualPrice =0;

        for (CartItem item: order.getCartItemList()) {
            double actualPrice = priceCalculator.calculatePrice(item);
            item.setActual_price(actualPrice);
            double optimalPrice = optimalPriceCalculator.calculatePrice(item);
            item.setOptimal_price(optimalPrice);
            totalOptimalPrice += optimalPrice;
            totalActualPrice += actualPrice;
        }
        order.setTotalOptimalPrice(totalOptimalPrice);
        order.setTotalActualPrice(totalActualPrice);
        return order;
    }

    @Override
    public List<ProductPrice> getAllProductPriceList() {
        List<Product> products = productRepository.findAll();
        ArrayList<ProductPrice> allProductPrices = new ArrayList<>();
        if(!products.isEmpty()){
            for (Product product: products) {
                ProductPrice productPrice = new ProductPrice();
                productPrice.setProductId(product.getId());
                productPrice.setProductName(product.getProductName());
                Map<Integer,Double> priceList = new HashMap<>();
                priceList = calculateInitalPrice(product);
                productPrice.setPrices(priceList);
                allProductPrices.add(productPrice);

            }
            return allProductPrices;
        }
        return null;
    }

    public Map<Integer, Double> calculateInitalPrice(Product product){
        Map<Integer,Double> priceMap = new HashMap<Integer,Double>();
        int unitsPerCarton =product.getUnitsPerCarton();
        Double singleUnitPrice = product.getCartonPrice()/unitsPerCarton;
        int minUnit =1;
        int maxUnit =50;
        double price=0;
        for(int i=minUnit; i<=maxUnit; i++){
            price = i*singleUnitPrice;
            priceMap.put(i,price);
        }
        return priceMap;
    }

}
