package com.phone.api.service.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phone.api.domain.District} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DistrictDTO implements Serializable {

    @Id
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 0, max = 100)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 0, max = 300)
    private String description;

    @NotNull
    private Integer code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

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

    @Override
    public String toString() {
        return "DistrictDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", code=" + code +
            '}';
    }
}
