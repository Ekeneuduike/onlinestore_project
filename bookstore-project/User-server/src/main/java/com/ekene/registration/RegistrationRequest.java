package com.ekene.registration;

import com.ekene.module.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegistrationRequest {
private String firstname;
private String lastname;
private String email;
private String password;
private Date dob;
}
