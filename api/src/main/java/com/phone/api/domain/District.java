package com.phone.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A District.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "district")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "code", nullable = false)
    private Integer code;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "district")
    @JsonIgnoreProperties(value = {"district"}, allowSetters = true)
    private Set<Customer> customers = new HashSet<>();

    public District(Long id, String name, String description, Integer code) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public District id(Long id) {
        this.setId(id);
        return this;
    }

    public District name(String name) {
        this.setName(name);
        return this;
    }

    public District description(String description) {
        this.setDescription(description);
        return this;
    }

    public District code(Integer code) {
        this.setCode(code);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof District)) {
            return false;
        }
        return getId() != null && getId().equals(((District) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
