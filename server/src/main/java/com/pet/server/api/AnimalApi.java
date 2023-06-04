package com.pet.server.api;

import com.pet.server.common.response.CommonResponse;
import com.pet.server.dto.AnimalDTO;
import com.pet.server.entity.Animal;
import com.pet.server.service.IAnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author Customer
 * */
@RestController
@RequestMapping("/api/animal")
public class AnimalApi {

    @Autowired
    private IAnimalService animalService;

    /**
     * @apiNote {role is Customer}
     * @param uid = 0: get all
     * @return List<Animation> of customer
     * */
    @GetMapping("/list")
    public CommonResponse<List<Map<String, Object>>> list(@RequestHeader("uid") Integer uid){
        return CommonResponse.of(HttpStatus.OK, true, "", animalService.getList(uid));
    }

    @GetMapping("/{id}")
    public CommonResponse<Animal> getById(@PathVariable("id") Integer id){
        return CommonResponse.of(HttpStatus.OK, true, "", animalService.getById(id));
    }

    /**
     * @apiNote {role is Customer}
     * @return Animation information after insert or update data
     * */
    @PostMapping("/save")
    public CommonResponse<Animal> save(@RequestHeader("uid") Integer uid
            , @RequestParam("id") Integer id
            , @RequestParam("avatar_url") String avatarUrl
            , @RequestParam("name") String name
            , @RequestParam("age") Double age
            , @RequestParam("species") String species
            , @RequestParam("description") String description
            , @RequestParam("id_status") Integer idStatus
            , @RequestParam(name = "avatar_file", required = false) MultipartFile avatarFile){
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setId(id);
        animalDTO.setIdUser(uid);
        animalDTO.setAvatarUrl(avatarUrl);
        animalDTO.setName(name);
        animalDTO.setAge(age);
        animalDTO.setSpecies(species);
        animalDTO.setIdStatus(idStatus);
        animalDTO.setDescription(description);
        animalDTO.setAvatarFile(avatarFile);
        return CommonResponse.of(HttpStatus.OK, true, "", animalService.save(animalDTO));
    }
}
