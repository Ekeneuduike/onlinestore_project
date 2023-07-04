package com.ekene.repository;

import com.ekene.registration.RegistrationToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RegistrationRepo extends JpaRepository<RegistrationToken,Integer> {
   Optional <RegistrationToken> findBytoken(String token);

   @Transactional
   @Modifying
   @Query("update RegistrationToken t set t.confirmAt = ?2 where token = ?1")
    void setConfirmationTime(String token, LocalDateTime now);
}
