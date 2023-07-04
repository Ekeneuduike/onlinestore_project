package com.ekene.mailcongfig;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
public class MailBuilder {

    @Autowired
  private JavaMailSender javaMailSender;
    public void sendEmail(String email,String body,String subject){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        simpleMailMessage.setFrom("ekeneuduike1@outlook.com");
        javaMailSender.send(simpleMailMessage);

    }
}
