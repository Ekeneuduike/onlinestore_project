package com.ekene.config;

import com.ekene.dto.MassageBuilderProperties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private String bootstrapServer = "kafka://localhost:29092";


    public Map<String,Object> producerconfig(){
        HashMap<String,Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, MassageBuilderProperties> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerconfig());
    }


    @Bean
    public KafkaTemplate<String , MassageBuilderProperties> kafkaTemplate
            (ProducerFactory<String, MassageBuilderProperties> producerFactory){
        return new KafkaTemplate<>(producerFactory);
        }

}