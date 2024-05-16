package com.phone.api.repository;

import com.phone.api.domain.Phone;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

//    @Query(value = "SELECT A.ID, A.CUSTOMER_ID, A.PHONE_NUMBER, \n" +
//        "B.ID AS CUSTOMER_ID, B.DISTRICT_ID, B.ADDRESS, B.EMAIL, B.FIRST_NAME, B.LAST_NAME, \n" +
//        "C.ID AS DISTRIT_ID, C.CODE, C.DESCRIPTION, C.NAME FROM PHONELIST A \n" +
//        "INNER JOIN CUSTOMER B ON B.ID = A.CUSTOMER_ID\n" +
//        "INNER JOIN DISTRICT C ON C.ID = B.DISTRICT_ID", nativeQuery = true)
//    void findAllPhoneAndCustomerAndDistrict(Pageable pageable);

}
