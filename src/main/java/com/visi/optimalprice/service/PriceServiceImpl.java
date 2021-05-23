package com.visi.optimalprice.service;

import com.visi.optimalprice.model.CartItem;
import com.visi.optimalprice.model.Order;
import com.visi.optimalprice.model.Product;
import com.visi.optimalprice.model.ProductPrice;
import com.visi.optimalprice.repository.OrderRepository;
import com.visi.optimalprice.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PriceServiceImpl implements PriceService{

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Map<Integer, Double> getInitialPriceData(long productId) {
        Product product =getProductById(productId);
        return calculateInitalPrice(product);
    }

    @Override
    public Order calculateOptimalPrice(Order order) {
        double totalOptimalPrice =0;
        double totalActualPrice =0;
        for (CartItem item: order.getCartItemList()) {
            String productId = item.getProduct_id();
            Product product = getProductById(Long.parseLong(productId));
            double singleUnitPrice = product.getCartonPrice()/product.getUnitsPerCarton();
            double actualPrice = calculateActualPrice(item.getQuantity(), singleUnitPrice);
            item.setActual_price(actualPrice);
            double optimalPrice = calculateOptimalPrice(item);
            item.setOptimal_price(optimalPrice);
            totalOptimalPrice = totalOptimalPrice+ optimalPrice;
            totalActualPrice = totalActualPrice+ actualPrice;
        }
        order.setTotalOptimalPrice(totalOptimalPrice);
        order.setTotalActualPrice(totalActualPrice);
        return order;
    }

    @Override
    public List<ProductPrice> getAllProductPriceList() {
        List<Product> products = priceRepository.findAll();
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

    public Product getProductById(long productId){
        Optional<Product> optional = priceRepository.findById(productId);
        Product product = null;
        if(optional.isPresent()){
            product = optional.get();
        } else {
            throw new RuntimeException("Product is not available with id : "+ productId);
        }
        return product;
    }

    public Map<Integer, Double> calculateInitalPrice(Product product){
        Map<Integer,Double> priceMap = new HashMap<Integer,Double>();
        int unitsPerCarton =product.getUnitsPerCarton();
        Double singleUnitPrice = product.getCartonPrice()/unitsPerCarton;
        int minUnit =1;
        int maxUnit =50;
        double price=0;
        int carton =0;
        int remainingUnits =0;

        for(int i=minUnit; i<=maxUnit; i++){
            price = i*singleUnitPrice;
            priceMap.put(i,price);
        }
        return priceMap;
    }

    public double calculateActualPrice(int purchasedUnit, double singleUnitPrice){
        return purchasedUnit*singleUnitPrice;
    }

    public double calculateOptimalPrice(CartItem item){
        String productId = item.getProduct_id();
        Product product = getProductById(Long.parseLong(productId));
        double singleUnitPrice = product.getCartonPrice()/product.getUnitsPerCarton();
        double actualPrice = calculateActualPrice(item.getQuantity(), singleUnitPrice);

        int numOfCartons = item.getQuantity()/product.getUnitsPerCarton();
        int remainingUnits = item.getQuantity() % product.getUnitsPerCarton();

        double optimalPrice =0;

        if(numOfCartons >0 ){
            optimalPrice =optimalPrice + (numOfCartons*product.getCartonPrice());
            if(numOfCartons>=3){
                double discount = addDiscount(numOfCartons,product.getCartonPrice());
                optimalPrice = optimalPrice - discount;
            }

            if(remainingUnits > 0){
                //optimalPrice = optimalPrice + (remainingUnits* singleUnitPrice);
                //add compensation
                double compensation = product.getCartonPrice() * 1.3;
                optimalPrice = optimalPrice +compensation;
            }
        }
        return optimalPrice;
    }

    public double addDiscount(int numOfCartons, double cartonPrice){
        double discount = numOfCartons *(0.1*cartonPrice);
        return discount;
    }
}
