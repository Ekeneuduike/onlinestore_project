package com.ekene.module.dto;

import com.ekene.module.OrderItem;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String orderId;
    private LocalDate dateOfOrder;
    private List<OrderItemRequest> orderItemRequests;
    private float price;
}
