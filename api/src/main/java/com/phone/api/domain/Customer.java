package com.phone.api.domain;

import com.phone.api.annotation.JsonSerializable;
import jakarta.persistence.*;

@Entity
@JsonSerializable
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
