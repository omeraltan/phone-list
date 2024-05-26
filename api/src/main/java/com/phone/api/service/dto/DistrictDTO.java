package com.phone.api.service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phone.api.domain.District} entity.
 */

@SuppressWarnings("common-java:DuplicatedBlocks")
public class DistrictDTO implements Serializable {

    @Column(name = "id")
    private Long id;

    @NotBlank(message = "{error.validation.empty.name}")
    @NotNull(message = "{error.validation.null.name}")
    @Size(min = 3, max = 20, message = "{error.validation.invalid.size.name}")
    private String name;

    @NotBlank(message = "{error.validation.empty.description}")
    @NotNull(message = "{error.validation.null.description}")
    @Size(min = 10, max = 150, message = "{error.validation.invalid.size.description}")
    private String description;

    @NotNull(message = "{error.validation.null.code}")
    private Integer code;

    public DistrictDTO() {
    }

    public DistrictDTO(Long id) {
        this.id = id;
    }

    public DistrictDTO(String name, String description, Integer code) {
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public DistrictDTO(Long id, String name, String description, Integer code) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
    }

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
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DistrictDTO that)) return false;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
