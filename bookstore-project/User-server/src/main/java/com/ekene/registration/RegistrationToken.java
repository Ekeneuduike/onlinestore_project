package com.ekene.registration;

import com.ekene.module.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationToken {
    @Id
    @GeneratedValue(generator = "tokenid",strategy = GenerationType.UUID)
    @SequenceGenerator(name = "tokenid",sequenceName = "tokenid")
    private Integer id;
    private String token;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmAt;
    private LocalDateTime createdAt;
    @ManyToOne()
    @JoinColumn(name = "token_id", referencedColumnName = "id")
    private User user;
}
