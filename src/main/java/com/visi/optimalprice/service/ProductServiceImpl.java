package com.visi.optimalprice.service;

import com.visi.optimalprice.model.Product;
import com.visi.optimalprice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product getProductById(long productId) {
        Optional<Product> optional = productRepository.findById(productId);
        Product product = null;
        if(optional.isPresent()){
            product = optional.get();
        } else {
            throw new RuntimeException("Product is not available with id : "+ productId);
        }
        return product;
    }
}
