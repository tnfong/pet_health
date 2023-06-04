package com.pet.server.service;

import com.pet.server.dto.AnimalDTO;
import com.pet.server.entity.Animal;

import java.util.List;
import java.util.Map;

public interface IAnimalService {
    List<Map<String, Object>> getList(Integer uid);
    Animal getById(Integer id);
    Animal save(AnimalDTO animalDTO);
}
