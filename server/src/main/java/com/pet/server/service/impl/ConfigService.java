package com.pet.server.service.impl;

import com.pet.server.dto.ConfigDTO;
import com.pet.server.entity.Config;
import com.pet.server.repository.ConfigRepository;
import com.pet.server.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigService implements IConfigService {

    @Autowired
    private ConfigRepository configRepository;

    @Override
    public List<Config> getList() {
        return configRepository.findAll();
    }

    @Override
    public Config getByKey(String key) {
        return configRepository.findByKey(key);
    }

    @Override
    public Config update(ConfigDTO configDTO) {
        Config config = new Config(configDTO);
        configRepository.save(config);
        return config;
    }
}
