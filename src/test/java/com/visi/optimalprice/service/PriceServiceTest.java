package com.visi.optimalprice.service;

import com.visi.optimalprice.model.CartItem;
import com.visi.optimalprice.model.Order;
import com.visi.optimalprice.model.Product;
import com.visi.optimalprice.model.ProductPrice;
import com.visi.optimalprice.repository.OrderRepository;
import com.visi.optimalprice.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceServiceTest {

    @Autowired
    private PriceService priceService;

    @MockBean
    private PriceRepository priceRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void testGetAllProductPriceList(){
        /*ArrayList<ProductPrice> allProductPrices = new ArrayList<>();
        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(1);
        productPrice.setProductName("Horseshoe");
        Map<Integer, Double> price = new HashMap<>();
        price.put(1,2.5);
        price.put(2,5.0);
        productPrice.setPrices(price);
        allProductPrices.add(productPrice);*/

        Product product = new Product();
        product.setId(1l);
        product.setProductName("Horseshoe");
        product.setCartonPrice(5.0);
        product.setUnitsPerCarton(2);

        ArrayList<Product> products = new ArrayList<Product>();
        products.add(product);

        Mockito.when(priceRepository.findAll()).thenReturn(products);
        assertThat(priceService.getAllProductPriceList().get(0).getPrices().size()).isEqualTo(50);

    }

    @Test
    public void testcalculateOptimalPrice(){
        Order order = new Order();
        ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
        CartItem cartItem = new CartItem();
        cartItem.setProduct_id("1");
        cartItem.setQuantity(22);
        cartItems.add(cartItem);
        order.setCartItemList(cartItems);

        Product product = new Product();
        product.setId(1l);
        product.setProductName("Penguin-ears");
        product.setCartonPrice(175);
        product.setUnitsPerCarton(20);
        Optional<Product> productOptional =Optional.of(product);

        Mockito.when(priceRepository.findById(1L)).thenReturn(productOptional);
        assertThat(priceService.calculateOptimalPrice(order).getCartItemList().get(0).getOptimal_price()).isEqualTo(402.5);

    }
}
