package com.pet.server.repository;

import com.pet.server.dto.BookDTO;
import com.pet.server.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "SELECT b.id AS id" +
            ", b.idUser AS idUser" +
            ", u.fullName AS userFullName" +
            ", u.avatarUrl AS userAvatarUrl" +
            ", u.phone AS userPhone" +
            ", b.idDoctor AS idDoctor" +
            ", u1.fullName AS doctorFullName" +
            ", u1.avatarUrl AS doctorAvatarUrl" +
            ", u1.phone AS doctorPhone" +
            ", b.idAnimal AS idAnimal" +
            ", a.name AS animalName" +
            ", a.avatarUrl AS animalAvatarUrl" +
            ", b.idStatus AS idStatus" +
            ", s.name AS statusName" +
            ", b.photoUrl As photoUrl" +
            ", b.time AS time" +
            ", b.timeHold AS timeHold" +
            ", b.location AS location" +
            ", b.description AS description" +
            ", b.idService AS idService" +
            ", sv.name AS serviceName" +
            ", b.createdDate AS createdDate" +
            ", b.updatedDate AS updatedDate " +
            ", bi.id AS idBill " +
            ", g.id AS idGuest " +
            " FROM Book b " +
            " INNER JOIN User u ON b.idUser = u.id " +
            " LEFT JOIN User u1 ON b.idDoctor = u1.id " +
            " INNER JOIN Animal a ON b.idAnimal = a.id " +
            " INNER JOIN Status s ON b.idStatus = s.id " +
            " INNER JOIN Service sv ON b.idService = sv.id " +
            " LEFT JOIN Guest g ON b.id = g.idBook " +
            " LEFT JOIN Bill bi ON b.id = bi.idBook " +
            " WHERE (:#{#search.idStatus} = 0 OR b.idStatus = :#{#search.idStatus}) " +
            " ORDER BY u1.fullName ASC, b.id DESC ",
        countQuery = "SELECT COUNT(b.id) " +
            " FROM Book b " +
            " INNER JOIN User u ON b.idUser = u.id " +
            " LEFT JOIN User u1 ON b.idDoctor = u1.id " +
            " INNER JOIN Animal a ON b.idAnimal = a.id " +
            " INNER JOIN Status s ON b.idStatus = s.id "+
            " WHERE (:#{#search.idStatus} = 0 OR b.idStatus = :#{#search.idStatus}) ")
    Page<Map<String, Object>> getDataPage(@Param("search") BookDTO search, Pageable pageable);

    @Query("SELECT b.id AS id" +
            ", b.idUser AS idUser" +
            ", u.fullName AS userFullName" +
            ", u.avatarUrl AS userAvatarUrl" +
            ", u.phone AS userPhone" +
            ", b.idDoctor AS idDoctor" +
            ", u1.fullName AS doctorFullName" +
            ", u1.avatarUrl AS doctorAvatarUrl" +
            ", u1.phone AS doctorPhone" +
            ", b.idAnimal AS idAnimal" +
            ", a.name AS animalName" +
            ", a.avatarUrl AS animalAvatarUrl" +
            ", b.idStatus AS idStatus" +
            ", s.name AS statusName" +
            ", b.photoUrl As photoUrl" +
            ", b.time AS time" +
            ", b.timeHold AS timeHold" +
            ", b.location AS location" +
            ", b.description AS description" +
            ", b.idService AS idService" +
            ", sv.name AS serviceName" +
            ", b.createdDate AS createdDate" +
            ", b.updatedDate AS updatedDate " +
            ", g.id AS idGuest " +
            ", bi.id AS idBill " +
            " FROM Book b " +
            " INNER JOIN User u ON b.idUser = u.id " +
            " LEFT JOIN User u1 ON b.idDoctor = u1.id " +
            " INNER JOIN Animal a ON b.idAnimal = a.id " +
            " INNER JOIN Status s ON b.idStatus = s.id " +
            " INNER JOIN Service sv ON b.idService = sv.id " +
            " LEFT JOIN Guest g ON b.id = g.idBook " +
            " LEFT JOIN Bill bi ON b.id = bi.idBook " +
            " WHERE (:#{#search.idStatus} = 0 OR b.idStatus = :#{#search.idStatus}) " +
            " AND (:#{#search.idUser} = 0 OR b.idUser = :#{#search.idUser}) " +
            " AND (:#{#search.idDoctor} = 0 OR b.idDoctor = :#{#search.idDoctor}) " +
            " ORDER BY u1.fullName ASC, b.id DESC ")
    List<Map<String, Object>> getList(@Param("search") BookDTO search);

    @Query("SELECT b " +
            " FROM Book b" +
            " WHERE (:time BETWEEN b.time AND b.timeHold) " +
            " OR (:time_hold BETWEEN b.time AND b.timeHold) ")
    List<Book> getDataByTime(@Param("time") Date time, @Param("time_hold") Date timeHold);

    @Query(" SELECT b.id AS id" +
            ", b.idUser AS idUser" +
            ", u.fullName AS userFullName" +
            ", u.phone AS userPhone " +
            ", b.idDoctor AS idDoctor" +
            ", ud.fullName AS doctorFullName" +
            ", ud.phone AS doctorPhone " +
            ", b.idAnimal AS idAnimal" +
            ", a.name AS animalName" +
            ", a.species AS animalSpecies" +
            ", b.idStatus AS idStatus" +
            ", b.idService AS idService" +
            ", s.name AS serviceName" +
            ", b.photoUrl AS photoUrl" +
            ", b.time AS time" +
            ", b.timeHold AS timeHold" +
            ", b.location AS location" +
            ", b.description AS description" +
            ", b.createdDate AS createdDate" +
            ", b.updatedDate AS updatedDate " +
            " FROM Book b " +
            " LEFT JOIN User ud ON b.idDoctor = ud.id " +
            " LEFT JOIN User u ON b.idUser = u.id " +
            " LEFT JOIN Animal a ON b.idAnimal = a.id " +
            " LEFT JOIN Service s ON b.idService = s.id " +
            " WHERE b.id = :id ")
    Map<String, Object> getInfo(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Book b WHERE b.idUser = :id_user OR b.idDoctor = :id_user")
    void deleteByIdUser(@Param("id_user") Integer idUser);

}
