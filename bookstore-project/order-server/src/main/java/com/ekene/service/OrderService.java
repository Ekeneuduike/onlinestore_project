package com.ekene.service;

import com.ekene.exceptionconfig.CustomException;
import com.ekene.module.Order;
import com.ekene.module.OrderItem;
import com.ekene.module.dto.InventoryResponse;
import com.ekene.module.dto.OrderItemRequest;
import com.ekene.module.dto.OrderRequest;
import com.ekene.module.dto.OrderResponse;
import com.ekene.repo.OrderRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,Object> kafkaTemplate;
    public void order(OrderRequest orderRequest) {
       List<OrderItem> orderItems = orderRequest.getOrderItemRequests().stream().map(orderItemRequest ->
                OrderItem.builder()
                        .productName(orderItemRequest.getProductName())
                        .author(orderItemRequest.getAuthor())
                        .description(orderItemRequest.getDescription())
                        .price(orderItemRequest.getPrice())
                        .quantity(orderItemRequest.getQuantity()).build()).collect(Collectors.toList());
        List<String> productNames = orderItems.stream().map(OrderItem::getProductName).toList();
        log.info("these are "+productNames);



        // maps name and quantity of product to orderResponses
        List<OrderResponse> orderResponses = orderRequest.getOrderItemRequests()
                .stream().map(orderItemRequest ->
                        new OrderResponse(orderItemRequest.getProductName(),
                        orderItemRequest.getQuantity())).toList();

        // mapping to order
        Order order = new Order();
        order.setDateOfOrder(LocalDate.now());
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderItems(orderItems);
        order.setPrice(getTotalprice(orderRequest));


        // request to inventory service
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-server/api/inventory/isinstock",
                 uriBuilder -> uriBuilder.queryParam("productNames",productNames)
                        .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        log.info("response "+inventoryResponses);

        assert inventoryResponses != null;
        log.info("hello");
        boolean isInStock = isInStock(inventoryResponses,orderRequest.getOrderItemRequests());
        log.info("yes");

        if(isInStock){
            kafkaTemplate.send("orderNotification",InventoryResponse[].class);
            //Todo: send request to payment service before proceeding

            // updates inventory after a successful purchase
            webClientBuilder.build().patch().uri("http://inventory-server/api/inventory/updatequantity",
                    uriBuilder -> uriBuilder.queryParam("productNames",productNames).build())
                    .bodyValue(orderResponses)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        orderRepo.save(order);
        log.info("save");
    }
    else {
        throw new RuntimeException("some products not available");
        }
    }

    private boolean isInStock(InventoryResponse[] inventoryResponses,
                              List<OrderItemRequest> orderItemRequests) {


        boolean isinstock;
        for (int i = 0; i < inventoryResponses.length; i++) {
            if (inventoryResponses[i].getProductName()
                    .equalsIgnoreCase(orderItemRequests.get(i).getProductName())) {
                 isinstock = inventoryResponses[i]
                         .getQuantity() >= orderItemRequests.get(i).getQuantity();
                inventoryResponses[i].setIsInStock(isinstock);
                log.info("isinstock"+inventoryResponses[i]);
            }
            else {

                if (inventoryResponses[i].getIsInStock().equals(false)) {
                    List<InventoryResponse> productsNotAvailable = new ArrayList<>();
                    productsNotAvailable.add(inventoryResponses[i]);
                }


                throw new RuntimeException("these products are not in stock " +
                        inventoryResponses[i]
                        .getProductName() +
                        inventoryResponses[i].getQuantity());
            }
        }

        boolean hmmm = Arrays.stream(inventoryResponses)
                .allMatch(InventoryResponse::getIsInStock);
        log.info(""+hmmm);
        return hmmm;
    }


    private float getTotalprice(OrderRequest orderRequest) {

        List<OrderItem> orderItems = orderRequest.getOrderItemRequests().stream().map(orderItemRequest ->
                OrderItem.builder()
                        .productName(orderItemRequest.getProductName())
                        .author(orderItemRequest.getAuthor())
                        .description(orderItemRequest.getDescription())
                        .price(orderItemRequest.getPrice()*orderItemRequest.getQuantity())
                        .quantity(orderItemRequest.getQuantity()).build()).toList();
        List<Float> prices = new ArrayList<>(orderItems.stream().map(OrderItem::getPrice).toList());
        float totalPrice = 0;
        for (int i = 0; i < prices.size(); i++){

            totalPrice = (float) totalPrice + prices.get(i);
    }
        log.info("not the problem  " + totalPrice);
        return totalPrice;
    }

}
