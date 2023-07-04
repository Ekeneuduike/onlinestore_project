package com.ekene;

import com.ekene.dto.MassageBuilderProperties;
import com.ekene.mailcongfig.MailBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@AllArgsConstructor
@SpringBootApplication
public class NotificationServer {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServer.class,args);
    }
    private MailBuilder mailBuilder;
    @KafkaListener(topics = "accountConfirmation",id = "groupId")
    public void massageBuilder( MassageBuilderProperties massageBuilderProperties){
        log.info("click here to confirm user {}",massageBuilderProperties);
        mailBuilder.sendEmail("ekeneuduike@gmail.com",
                massageBuilderProperties.getMassage(),"confirm email");
    }

}