package com.example.sendEmail.role;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role implements GrantedAuthority {

    public static final Long ROLE_ADMIN_ID = 1L;
    public static final Long ROLE_USER_ID = 2L;

    public static final String ROLE_ADMIN_NAME = "ROLE_ADMIN";
    public static final String ROLE_USER_NAME = "ROLE_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
