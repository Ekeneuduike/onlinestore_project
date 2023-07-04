package com.ekene.service;

import com.ekene.module.Product;
import com.ekene.module.UpdateInfo;
import com.ekene.module.dto.ProductDto;
import com.ekene.module.dto.ProductResponse;
import com.ekene.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {
 private final ProductRepository productRepository;
 private final WebClient.Builder webClient;
 private final KafkaTemplate<String,Object> kafkaTemplate;
    public Object addproduct(ProductResponse productResponse) throws InterruptedException {
        Product product = productRepository.findByproductName(productResponse.getProductName());
        if (product == null) {
            product = Product.builder()
                    .author(productResponse.getAuthor())
                    .description(productResponse.getDescription())
                    .quantity(productResponse.getQuantity())
                    .price(productResponse.getPrice())
                    .productName(productResponse.getProductName())
                    .productAdditionTime(LocalDateTime.now())
                    .build();
            // kafka massage
            kafkaTemplate.send("newProductNotification", productResponse);
            // stocking inventory
            Thread.sleep(1000 * 10);
            webClient.build().post().uri("http://inventory-server/api/inventory/stock")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(product)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            log.info("updated successfully");
            return productRepository.save(product);
        }
        else {
            throw new RuntimeException("productName already exist");
        }

    }

    public ProductDto getProduct(String name) {
        Product product = productRepository.findByproductName(name);
       return ProductDto.builder()
                .author(product.getAuthor())
                .productName(product.getProductName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

    public List<ProductDto> getAllProduct() throws InterruptedException {

        return productRepository.findAll().stream().map(product ->
            new ProductDto(
                    product.getProductName(),
                    product.getDescription(),
                    product.getAuthor(),
                    product.getPrice()
            )
        ).collect(Collectors.toList());
    }

    public void deleteProduct(List<String> names) {
        productRepository.deleteByproductNameIn(names);
    }

    public String updateProductService(String name,UpdateInfo updateInfo) {
        Product product = productRepository.findByproductName(name);
                product.setUpdatedAt(LocalDate.now());
                product.setProductName(updateInfo.productName());
                product.setAuthor(updateInfo.author());
                product.setQuantity(updateInfo.quantity());
                product.setDescription(updateInfo.description());

        // kafka massage
        kafkaTemplate.send("updateNotification", updateInfo);

        //updating inventory service
        webClient.build().put()
                        .uri("http://INVENTORY-SERVICE/api/inventory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updateInfo)
                        .retrieve()
                        .bodyToMono(UpdateInfo.class).block();

        productRepository.save(product);
        return "update successful";
    }

    public void changePrice(String name,Double price, String percent) {
        Product product = productRepository.findByproductName(name);
        if (price == null&& percent != null) {
            if (percent.startsWith("-")) {
                String substring = percent.substring(0, percent.length() - 1);
                double percentage = Integer.parseInt(substring);
                double newPrice = ((percentage * product.getPrice()) / (-100)) + product.getPrice();
                product.setPrice(newPrice);
                productRepository.save(product);
                kafkaTemplate.send("priceUpdated",product.getPrice());
            } else {
                String substring = percent.substring(0, percent.length() - 1);
                double percentage = Integer.parseInt(substring);
                double newPrice = ((percentage * product.getPrice()) / 100) + product.getPrice();
                product.setPrice(newPrice);
                productRepository.save(product);
                kafkaTemplate.send("priceUpdated",product.getPrice());
            }
        }

            else {
                double newPrice = price + product.getPrice();
               product.setPrice(newPrice);
               productRepository.save(product);
            kafkaTemplate.send("priceUpdated",product.getPrice());
            }



        }
    }

