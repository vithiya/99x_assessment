package com.visi.optimalprice.controller;

import com.visi.optimalprice.model.Order;
import com.visi.optimalprice.model.ProductPrice;
import com.visi.optimalprice.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

//@CrossOrigin(origins={"http://localhost:3000","http://localhost:8080"})
@RestController
public class PriceController {

    @Autowired
    private PriceService priceService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping ("/initial_price/{product_id}")
    public Map<Integer,Double> getProductPriceListView(@PathVariable(value = "product_id") long id){
      Map<Integer,Double> priceMap = new HashMap<Integer,Double>();
      priceMap = priceService.getInitialPriceData(id);
      return priceMap;
    }
    @GetMapping ("/initial_price")
    public List<ProductPrice> getAllProductPriceList(){
        List<ProductPrice> priceList = null;
        priceList = priceService.getAllProductPriceList();
        return priceList;
    }

    @PostMapping ("/optimal_price")
    public Order processOptimalPrice(@RequestBody Order order){
        priceService.calculateOptimalPrice(order);
        return order;
    }
}
