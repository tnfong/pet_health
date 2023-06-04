package com.pet.server.service.impl;

import com.pet.server.dto.AnimalDTO;
import com.pet.server.entity.Animal;
import com.pet.server.repository.AnimalRepository;
import com.pet.server.service.IAnimalService;
import com.pet.server.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AnimalService implements IAnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private IFileService fileService;

    @Override
    public List<Map<String, Object>> getList(Integer uid) {
        return animalRepository.getList(uid);
    }

    @Override
    public Animal getById(Integer id) {
        return animalRepository.findById(id).orElse(null);
    }

    @Override
    public Animal save(AnimalDTO animalDTO) {
        Animal animal = new Animal(animalDTO);
        if(animalDTO.getAvatarFile() != null && animalDTO.getAvatarFile().getSize() > 0){
            animal.setAvatarUrl(fileService.uploadFile(animalDTO.getAvatarFile(), ""));
        }
        animalRepository.save(animal);
        return animal;
    }
}
