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

    @NotBlank(message = "{error.validation.empty.address}")
    @NotNull(message = "{error.validation.null.address}")
    @Size(min = 10, max = 200, message = "{error.validation.invalid.size.address}")
    private String address;

    private DistrictDTO districtDTO;

    public CustomerDTO() {
    }

    public CustomerDTO(Long id) {
        this.id = id;
    }

    public CustomerDTO(Long id, String firstName, String lastName, String email, String address, DistrictDTO districtDTO) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.districtDTO = districtDTO;
    }

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
        if (firstName == null){
            this.firstName = null;
        }
        else {
            this.firstName = firstName.trim().toUpperCase();
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null){
            this.lastName = null;
        }
        else {
            this.lastName = lastName.trim().toUpperCase();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DistrictDTO getDistrictDTO() {
        return districtDTO;
    }

    public void setDistrictDTO(DistrictDTO districtDTO) {
        this.districtDTO = districtDTO;
    }

    public String getJoinName(){
        if (getFirstName() == null && getLastName() == null) {
            return null;
        }
        else {
            return getFirstName() + " " + getLastName();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDTO)) {
            return false;
        }

        CustomerDTO customerDTO = (CustomerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
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
