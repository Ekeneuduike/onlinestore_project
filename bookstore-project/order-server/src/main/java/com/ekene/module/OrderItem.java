package com.ekene.module;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID,generator = "productId")
    @SequenceGenerator(name = "productId",sequenceName = "productId",allocationSize = 1)
    private Integer Id;
    private String productName;
    private String description;
    private String author;
    private Integer quantity;
    private float price;
}
