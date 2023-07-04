package com.ekene.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.time.LocalTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {
private String massage;
private Integer StatusCode;
private HttpStatus status;
private Map<?,?> data;
private LocalTime creationTime;

}
