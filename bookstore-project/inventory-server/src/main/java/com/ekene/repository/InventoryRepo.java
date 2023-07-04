package com.ekene.repository;

import com.ekene.module.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepo extends MongoRepository<Inventory,String> {

    Optional<Inventory> findByproductName(String name);

    List<Inventory> findByproductNameIn(List<String> productNames);



}
