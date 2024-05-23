package com.phone.api.repository;

import com.phone.api.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    /**
     * @param id We use the ID value to find the amount of customers in the phone table.
     * @return It will return information on how many times the customer used in the Phone table has been used.
     */
    @Query(value = "SELECT count(a) FROM Phone a WHERE a.customer.id = :id")
    Integer findCountByCustomerPhones(@Param("id") Long id);

}
