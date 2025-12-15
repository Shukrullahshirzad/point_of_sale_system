package com.shirzad.pointOfSaleSystem.modal;

import com.shirzad.pointOfSaleSystem.domain.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User {

    @Id
    private Long id;

    @Column(nullable = false)
    private String fullName;


    @Column(nullable = false, unique = true)
    @Email(message = "Email should be in valid format")
    private String email;

    private String phoneNumber;
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserRole role;

    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate lastLogin;




}
