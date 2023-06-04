package com.pet.server.repository;

import com.pet.server.dto.ServiceDTO;
import com.pet.server.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    @Query(value = " SELECT s " +
            " FROM Service s " +
            " WHERE (:#{#search.name} = '' OR s.name = :#{#search.name}) " +
            " ORDER BY s.id DESC ",
        countQuery = " SELECT COUNT(s.id) " +
                " FROM Service s "+
                " WHERE (:#{#search.name} = '' OR s.name = :#{#search.name}) ")
    Page<Service> getDataPage(@Param("search") ServiceDTO search, Pageable pageable);
}
