package com.ekene.repository;


import com.ekene.module.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,String> {
    Product findByproductName(String name);

    void deleteByproductNameIn(List<String> names);
}
