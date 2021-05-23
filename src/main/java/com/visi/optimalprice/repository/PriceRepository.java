package com.visi.optimalprice.repository;

import com.visi.optimalprice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Product,Long> {
}
