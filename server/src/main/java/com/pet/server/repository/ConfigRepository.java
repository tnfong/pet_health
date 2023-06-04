package com.pet.server.repository;

import com.pet.server.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<Config, Integer> {

    Config findByKey(String key);
}
