package com.ekene.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Inventory {
    @Id
    private String id;
    @Indexed(unique = true)
    private String productName;
    private String description;
    private String author;
    private Integer quantity;
    private double price;
    private LocalDateTime productAdditionTime;
    private LocalDate updatedAt;
}
