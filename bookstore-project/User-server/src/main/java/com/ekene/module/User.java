package com.ekene.module;

import com.ekene.registration.RegistrationToken;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_User")
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "userId",strategy = GenerationType.UUID)
    @SequenceGenerator(name = "userId",sequenceName = "userId")
    private Integer id;
    private String firstname;
    private String lastname;
    @Column(unique = true,nullable = false)
    private String email;

    private String password;
    private boolean enabled;
    private Date dob;
    @Enumerated(EnumType.STRING)
    private Role role;
    public User(String firstname, String lastname, String email, String password, Date dob) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
