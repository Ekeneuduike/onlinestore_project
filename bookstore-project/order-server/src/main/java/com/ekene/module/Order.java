package com.ekene.module;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "T_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID,generator = "orderId")
    @SequenceGenerator(name = "orderId",sequenceName = "orderId",allocationSize = 1)
    private Integer id;
    private String orderId;
    private LocalDate dateOfOrder;
    private float price;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
