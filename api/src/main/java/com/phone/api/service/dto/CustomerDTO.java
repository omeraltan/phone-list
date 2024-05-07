package com.phone.api.service.dto;

import com.phone.api.domain.District;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerDTO implements Serializable {

    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "{error.validation.empty.firstname}")
    @NotNull(message = "{error.validation.null.firstname}")
    @Size(min = 3, max = 50, message = "{error.validation.invalid.size.firstname}")
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @NotBlank(message = "{error.validation.empty.lastname}")
    @NotNull(message = "{error.validation.null.lastname}")
    @Size(min = 3, max = 50, message = "{error.validation.invalid.size.lastname}")
    @Column(name = "lastName", nullable = false)
    private String lastName;

    @NotBlank(message = "{error.validation.empty.email}")
    @NotNull(message = "{error.validation.null.email}")
    @Size(min = 10, max = 50, message = "{error.validation.invalid.size.email}")
    @Email(message = "{error.validation.constraints.email.message}")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @OneToOne
    private District district;
}
