package com.pet.server.repository;

import com.pet.server.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    @Query("SELECT a.id AS id" +
            ", a.idUser AS idUser" +
            ", a.name AS name" +
            ", a.avatarUrl AS avatarUrl" +
            ", a.age AS age" +
            ", a.species AS species" +
            ", a.idStatus AS idStatus" +
            ", s.name AS statusName " +
            "FROM Animal a " +
            "INNER JOIN Status s ON a.idStatus = s.id " +
            "WHERE (a.idUser = :id_user OR :id_user = 0)")
    List<Map<String, Object>> getList(@Param("id_user") Integer idUser);

    @Transactional
    void removeByIdUser(Integer idUser);
}
