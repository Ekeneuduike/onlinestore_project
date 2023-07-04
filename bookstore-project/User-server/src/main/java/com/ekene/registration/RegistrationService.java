package com.ekene.registration;

import com.ekene.module.User;
import com.ekene.dto.MassageBuilderProperties;
import com.ekene.repository.RegistrationRepo;
import com.ekene.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RegistrationService {
    private final UserService userService;
    private  final RegistrationRepo registrationRepo;
    private final KafkaTemplate<String, MassageBuilderProperties> kafkaTemplate;
    public String register(RegistrationRequest request) {

        //todo: regex check email
        String uri = userService.signUp( new User(request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                request.getPassword(),
                request.getDob()
                ));

     kafkaTemplate.send("accountConfirmation",new MassageBuilderProperties(uri,request.getEmail()));
       return "please check your email to complete registration " + uri + "  " + "<a href=\"http://user-server/api/user/confirm\">confirm account</a>";
    }

    @Transactional
    public String confirmUser(String token) {
        RegistrationToken registrationToken = registrationRepo
                .findBytoken(token).orElseThrow(()-> new IllegalStateException("no such token found"));
        if (registrationToken.getConfirmAt() != null){
            return "user already confirmed";
        } else if (registrationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            String token1 = UUID.randomUUID().toString();
            registrationToken.setToken(token1);
            return "token is expired new email has been sent check mail to confirm account" +
                    "http://localhost:8080/api/user/confirm?token=" + token1;
        }
        registrationRepo.setConfirmationTime(token,LocalDateTime.now());
        String email = registrationToken.getUser().getEmail();
        userService.enableUser(email);
        return "registration is complete please click  "+
               "http://localhost:8080/api/user/login  " + "<a href=\"http://localhost:8080/login\">here</a>";
    }
}
