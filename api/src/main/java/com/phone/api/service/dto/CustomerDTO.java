package com.phone.api.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phone.api.domain.Customer} entity.
 */
public class CustomerDTO implements Serializable {

    private Long id;

    @NotBlank(message = "{error.validation.empty.firstname}")
    @NotNull(message = "{error.validation.null.firstname}")
    @Size(min = 3, max = 50, message = "{error.validation.invalid.size.firstname}")
    private String firstName;

    @NotBlank(message = "{error.validation.empty.lastname}")
    @NotNull(message = "{error.validation.null.lastname}")
    @Size(min = 3, max = 50, message = "{error.validation.invalid.size.lastname}")
    private String lastName;

    @NotBlank(message = "{error.validation.empty.email}")
    @NotNull(message = "{error.validation.null.email}")
    @Size(min = 10, max = 50, message = "{error.validation.invalid.size.email}")
    @Email(message = "{error.validation.constraints.email.message}")
    private String email;

    private DistrictDTO districtDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DistrictDTO getDistrictDTO() {
        return districtDTO;
    }

    public void setDistrictDTO(DistrictDTO districtDTO) {
        this.districtDTO = districtDTO;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDTO that)) return false;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", districtDTO=" + districtDTO +
            '}';
    }
}
