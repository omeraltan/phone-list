package com.phone.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A Phone List
 */
@Entity
@Table(name = "phonelist")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Phone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"phones"}, allowSetters = true)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "Fk_phonelist_customer_id"))
    private Customer customer;

    public Phone() {
    }

    public Phone(Long id, String phoneNumber, Customer customer) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.customer = customer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone customer)) return false;

        return getId() != null && getId().equals(((Phone) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Phone{" +
            "id=" + id +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", customer=" + customer +
            '}';
    }
}
