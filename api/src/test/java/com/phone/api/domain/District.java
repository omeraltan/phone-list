package com.phone.api.domain;

import com.phone.api.domain.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "district")
public class District {

    @Id
    private Long id;
    private String name;
    private String description;
    private Integer code;
    @OneToOne
    private Customer customer;

    public District(long id, String name, String description, int code) {
    }
}
