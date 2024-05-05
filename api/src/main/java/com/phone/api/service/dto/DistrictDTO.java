package com.phone.api.service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DistrictDTO implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "{error.validation.empty.name}")
    @NotNull(message = "{error.validation.null.name}")
    @Size(min = 3, max = 10, message = "{error.validation.invalid.size.name}")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "{error.validation.empty.description}")
    @NotNull(message = "{error.validation.null.description}")
    @Size(min = 10, max = 150, message = "{error.validation.invalid.size.description}")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Invalid City: City is NULL")
    @Column(name = "code", nullable = false)
    private Integer code;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DistrictDTO)) {
            return false;
        }

        DistrictDTO districtDTO = (DistrictDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, districtDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
