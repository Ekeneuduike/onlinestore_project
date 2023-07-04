package com.ekene.module.dto;

import java.time.LocalDate;

public record UpdateInfo(String productName,
                         String description,
                         String author,
                         Integer quantity) {
}
