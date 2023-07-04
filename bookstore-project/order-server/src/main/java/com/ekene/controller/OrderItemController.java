package com.ekene.controller;

import com.ekene.OrderServer;
import com.ekene.module.dto.OrderRequest;
import com.ekene.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/order")
@AllArgsConstructor
public class OrderItemController {
    private final OrderService orderService;
    @PostMapping("placeorder")
    public String order(@RequestBody OrderRequest orderRequest){
        orderService.order(orderRequest);
        return "SUCCESS";
    }

}
