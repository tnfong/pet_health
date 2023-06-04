package com.pet.server.service;

import com.pet.server.dto.ConfigDTO;
import com.pet.server.entity.Config;

import java.util.List;

public interface IConfigService {
    List<Config> getList();
    Config getByKey(String key);
    Config update(ConfigDTO configDTO);
}
