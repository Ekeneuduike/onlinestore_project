package com.ekene.controller;

import com.ekene.module.dto.InventoryResponse;
import com.ekene.module.dto.OrderResponse;
import com.ekene.module.dto.ProductResponse;
import com.ekene.module.dto.UpdateInfo;
import com.ekene.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/inventory")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("stock")
    public String Stock(@RequestBody ProductResponse response){
        inventoryService.stock(response);
        return "inventory successfully stocked";
    }

    @PatchMapping("update/{name}")
    public String update(@PathVariable String name, @RequestBody UpdateInfo updateInfo){
        inventoryService.update(name,updateInfo);
        return "inventory successfully updated";
    }
    @PatchMapping("updatequantity")
    public String updateQuantity(@RequestParam List<String> productNames, @RequestBody OrderResponse[] orderResponse){
        inventoryService.updateQuantity(productNames,orderResponse);
        return "stock updated";
    }

    @GetMapping("isinstock")
    public List<InventoryResponse> isInStock(@RequestParam List<String> productNames){
        return inventoryService.isInStock(productNames);
    }
}
