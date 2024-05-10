package com.phone.api.repository;

import com.phone.api.domain.Customer;
import com.phone.api.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     *
     * @param id we use this id that to find districts that to have customers.
     * @return this method will return if we have a customers.
     */
    @Query("SELECT count(a) FROM Customer a WHERE a.district.id = :id")
    int findAllCustomersAmountThatUsingDistricts(@Param("id") Long id);

}
