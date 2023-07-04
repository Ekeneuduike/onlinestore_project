package com.ekene.controller;

import com.ekene.module.Response;
import com.ekene.module.UpdateInfo;
import com.ekene.module.dto.ProductDto;
import com.ekene.module.dto.ProductResponse;
import com.ekene.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PostMapping("add")
    public ResponseEntity<Response> responseResponseEntity(@RequestBody ProductResponse productResponse) throws InterruptedException {
        return ResponseEntity.ok(Response.builder()
                .massage("added" + productResponse.getProductName()+ "to inventory")
                .status(HttpStatus.CREATED)
                .StatusCode(HttpStatus.CREATED.value())
                .creationTime(LocalTime.now())
                .data(Map.of("added",productService.addproduct(productResponse)))
                .build());

    }

    @GetMapping("getproduct/{name}")
    public ProductDto productDto(@PathVariable String name){
        return productService.getProduct(name);
    }
    @CircuitBreaker(name = "product",fallbackMethod = "productFallBack")
    @GetMapping("getallproduct")
    public List<ProductDto> getAllProduct() throws InterruptedException {
        return productService.getAllProduct();
    }

    public List<ProductDto> productFallBack(Exception e){
        return Stream.of(
                new ProductDto("python on steroids","making programming fun","john",180.20),
                new ProductDto("life in the sea","fictional story","okeke",250.1)
        ).collect(Collectors.toList());
    }


    // http://localhost:8808/api/product/delete?name=enteredname
    @DeleteMapping("/delete")
    public String deleteProduct(@RequestParam List<String> names){
        productService.deleteProduct(names);
        return "deletion complete";
    }

    @PatchMapping("change price")
    public String changePrice(@PathVariable String name ,@RequestBody Double price,String percent){
        productService.changePrice(name,price,percent);
        return "price successfully updated";
    }


    @PatchMapping
    public String updateProductInfo(@PathVariable String name,@RequestBody UpdateInfo updateInfo){
     return productService.updateProductService(name,updateInfo);
    }
}
