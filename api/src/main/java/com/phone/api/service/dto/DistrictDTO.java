package com.phone.api.service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phone.api.domain.District} entity.
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DistrictDTO implements Serializable {

    @Column(name = "id")
    private Long id;

    @NotBlank(message = "{error.validation.empty.name}")
    @NotNull(message = "{error.validation.null.name}")
    @Size(min = 3, max = 20, message = "{error.validation.invalid.size.name}")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotBlank(message = "{error.validation.empty.description}")
    @NotNull(message = "{error.validation.null.description}")
    @Size(min = 10, max = 150, message = "{error.validation.invalid.size.description}")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Invalid City: City is NULL")
    @Column(name = "code", nullable = false)
    private Integer code;

}
