package com.pet.server.repository;

import com.pet.server.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    List<Status> findByTableName(String tableName);
}