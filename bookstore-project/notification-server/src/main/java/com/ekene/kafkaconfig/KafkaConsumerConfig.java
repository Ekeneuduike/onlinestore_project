package com.ekene.kafkaconfig;

import com.ekene.dto.MassageBuilderProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
//    @Value("${spring:kafka:bootstrap-servers}")
//    private String boostrapServers;
@Bean
ConcurrentKafkaListenerContainerFactory<String, MassageBuilderProperties>
kafkaListenerContainerFactory(ConsumerFactory<String,MassageBuilderProperties> consumerFactory)  {
    ConcurrentKafkaListenerContainerFactory<String,MassageBuilderProperties> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    return factory;
}


    public Map<String, Object> consumerConfig(){
        HashMap<String,Object> consume = new HashMap<>();
        consume.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"kafka://localhost:29092");
        consume.put(ConsumerConfig.CLIENT_ID_CONFIG,"groupId");
        consume.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        consume.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,ErrorHandlingDeserializer.class);
       // consume.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        //consume.put(JsonDeserializer.KEY_DEFAULT_TYPE, "com.example.MyKey");
        consume.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        consume.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.ekene.dto.MassageBuilderProperties");
        consume.put(JsonDeserializer.TRUSTED_PACKAGES, "com.ekene.*");
        return consume;
    }
    @Bean
    public ConsumerFactory<String,MassageBuilderProperties> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }
}
