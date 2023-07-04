package com.ekene.module;

import java.time.LocalDate;

public record UpdateInfo( String productName,
                          String description,
                          String author,
                          Integer quantity,
                          LocalDate updatedAt) {
}
