package com.ekene.service;

import com.ekene.module.Role;
import com.ekene.module.User;
import com.ekene.registration.RegistrationToken;
import com.ekene.repository.RegistrationRepo;
import com.ekene.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RegistrationRepo registrationRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()-> new IllegalStateException("user not found"));
    }

    public String signUp(User user) {
       boolean userExist = userRepository.findByEmail(user.getEmail()).isPresent();
        if (userExist) {

            return "you already have an account";

        }

        String password = user.getPassword();
        log.info("wait ended");
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        log.info("value encoded");
        user.setPassword(encodedPassword);
        user.setRole(Role.REGULAR);
        log.info("passing");
        userRepository.save(user);
        log.info("passed");
        String token1 = UUID.randomUUID().toString();
        RegistrationToken token = RegistrationToken.builder()
                .token(token1)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        registrationRepo.save(token);
        String uri = "http://localhost:8080/api/user/confirm?token=" + token1;

        return uri;
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }
}
