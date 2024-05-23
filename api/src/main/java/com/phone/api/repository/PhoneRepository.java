package com.phone.api.repository;

import com.phone.api.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    @Query(value = "SELECT count(a) FROM Phone a WHERE a.customer.id = :id")
    Integer findCountByCustomerPhones(@Param("id") Long id);

}
