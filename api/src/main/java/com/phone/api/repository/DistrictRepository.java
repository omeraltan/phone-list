package com.phone.api.repository;

import com.phone.api.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    /**
     * @param code The code parameter is minus one and we use it to find cities, not districts.
     * @return It will return the knowledge of all cities.
     */
    @Query("SELECT a FROM District a WHERE a.code = :code")
    List<District> findDistrictsByCodeIsLessThan(@Param("code") int code);

    /**
     * @param code The districts of the provinces are taken with the code parameter.
     * @return With this method, district information of the provinces will be returned.
     */
    @Query("SELECT a FROM District a WHERE a.code = :code")
    List<District> findDistrictsByCodeIsGreaterThanZero(@Param("code") int code);

}
