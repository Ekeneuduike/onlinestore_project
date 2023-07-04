package com.ekene.service;

import com.ekene.exception.CustomException;
import com.ekene.exception.CustomExceptionHandler;
import com.ekene.module.Inventory;
import com.ekene.module.dto.InventoryResponse;
import com.ekene.module.dto.OrderResponse;
import com.ekene.module.dto.ProductResponse;
import com.ekene.module.dto.UpdateInfo;
import com.ekene.repository.InventoryRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@AllArgsConstructor
@Service
@Slf4j
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    private final MongoTemplate mongoTemplate;
    public void stock(ProductResponse response) {
        Inventory inventory = Inventory.builder()
                .price(response.getPrice())
                .productName(response.getProductName())
                .productAdditionTime(LocalDateTime.now())
                .quantity(response.getQuantity())
                .description(response.getDescription())
                .author(response.getAuthor())
                .build();
        inventoryRepo.save(inventory);
        log.info("inventory updated");
    }

    public void update(String name, UpdateInfo updateInfo) {
        Inventory inventory = inventoryRepo.findByproductName(name)
                .orElseThrow(()-> new CustomException("no product with such name exist"));
        inventory.setUpdatedAt(LocalDate.now());
        inventory.setDescription(updateInfo.description());
        inventory.setProductName(updateInfo.productName());
        inventory.setQuantity(updateInfo.quantity());
        inventoryRepo.save(inventory);

    }

    public List<InventoryResponse> isInStock(List<String> productNames) {
       List<InventoryResponse> responses = inventoryRepo.
               findByproductNameIn(productNames).stream()
                .map(inventory -> InventoryResponse.builder()
                        .isInStock(inventory.getQuantity()>0)
                        .productName(inventory.getProductName())
                        .quantity(inventory.getQuantity()).build()).toList();
       log.info(" list are "+responses) ;
       return responses;

    }

    public void updateQuantity(List<String> productNames, OrderResponse[] orderResponse) {
        List<Inventory> inventoryList = inventoryRepo.findByproductNameIn(productNames);
        log.info("inventorylist  :"+inventoryList);
        for (int i = 0; i < inventoryList.size() ;i++ ){
            if (inventoryList.get(i).getProductName().equalsIgnoreCase(orderResponse[i].productName())) {
                inventoryList.get(i).setQuantity(inventoryList.get(i).getQuantity() - orderResponse[i].quantity());
                mongoTemplate.updateFirst(query(where("productName")
                        .is(inventoryList.get(i).getProductName())),
                        Update.update("quantity",inventoryList.get(i).getQuantity()),
                        Inventory.class);

            }
        }
    }
}
