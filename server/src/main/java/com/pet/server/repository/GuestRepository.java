package com.pet.server.repository;

import com.pet.server.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
    @Query("SELECT g.id AS id" +
            ", g.idDoctor AS idDoctor" +
            ", g.idAnimal AS idAnimal" +
            ", g.symptoms AS symptoms" +
            ", g.diagnostic AS diagnostic" +
            ", g.instructions AS instructions" +
            ", g.createdDate AS createdDate" +
            ", g.updatedDate AS updatedDate" +
            ", u.avatarUrl AS doctorUrl " +
            " FROM Guest g " +
            " INNER JOIN User u On g.idDoctor = u.id " +
            " INNER JOIN Animal a ON g.idAnimal = a.id " +
            " WHERE g.idAnimal = :id_animal " +
            " ORDER BY g.id DESC ")
    List<Map<String, Object>> getList(@Param("id_animal") Integer idAnimal);

    Guest findByIdBook(Integer idBook);

    @Modifying
    @Transactional
    @Query("DELETE FROM Guest g " +
            " WHERE g.idBook IN (SELECT b.id FROM Book b WHERE b.idUser = :id_user OR b.idDoctor = :id_user)")
    void deleteByUser(@Param("id_user") Integer idUser);
}
