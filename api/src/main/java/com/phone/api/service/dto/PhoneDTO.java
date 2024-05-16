package com.phone.api.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phone.api.domain.Phone} entity.
 */
public class PhoneDTO implements Serializable {

    private Long id;
    @NotBlank(message = "{error.validation.empty.phone}")
    @NotNull(message = "{error.validation.null.phone}")
    @Size(min = 16, max = 16, message = "{error.validation.invalid.size.phone}")
    private String phoneNumber;

    private CustomerDTO customerDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneDTO that)) return false;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PhoneDTO{" +
            "id=" + id +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", customerDTO=" + customerDTO +
            '}';
    }
}
