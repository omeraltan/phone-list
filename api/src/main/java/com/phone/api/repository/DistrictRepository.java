package com.phone.api.repository;

import com.phone.api.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query("SELECT a FROM District a WHERE a.code = :code")
    List<District> findDistrictsByCodeIsLessThan(@Param("code") int code);
}
