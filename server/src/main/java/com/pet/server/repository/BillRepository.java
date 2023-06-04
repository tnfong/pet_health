package com.pet.server.repository;

import com.pet.server.dto.BillDTO;
import com.pet.server.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    @Query("SELECT b.id AS id" +
            ", b.idBook AS idBook" +
            ", b.idUser AS idUser" +
            ", u.fullName AS userFullName" +
            ", u.phone AS userPhone" +
            ", b.price AS price" +
            ", b.note AS note" +
            ", b.createdDate AS createdDate " +
            " FROM Bill b " +
            " INNER JOIN User u ON b.idUser = u.id " +
            " INNER JOIN Book bk ON b.idBook = bk.id " +
            " WHERE (:id_user = 0 OR b.idUser = :id_user) ")
    List<Map<String, Object>> getList(@Param("id_user") Integer idUser);

    @Query(" SELECT u.id AS idUser " +
            ", u.fullName AS userFullName" +
            ", u.phone AS userPhone" +
            ", s.price AS servicePrice " +
            ", s.name AS serviceName " +
            " FROM Book b " +
            " INNER JOIN User u ON b.idUser = u.id " +
            " INNER JOIN Service s ON b.idService = s.id " +
            " WHERE b.id = :id_book ")
    Map<String, Object> initBill(@Param("id_book") Integer idBook);

    @Query("SELECT DATE_FORMAT(b.createdDate, '%Y/%m') AS time " +
            ", SUM(b.price) AS totalPrice " +
            "FROM Bill b " +
            "GROUP BY MONTH(b.createdDate) ")
    List<Map<String, Object>> analysisByMonth();

    @Query("SELECT WEEK(b.createdDate) AS time " +
            ", SUM(b.price) AS totalPrice " +
            "FROM Bill b " +
            "GROUP BY WEEK(b.createdDate) ")
    List<Map<String, Object>> analysisByWeek();

    Bill findByIdBook(Integer idBook);

    @Modifying
    @Transactional
    @Query("DELETE FROM Bill bi " +
            " WHERE bi.idBook IN (SELECT b.id FROM Book b WHERE b.idUser = :id_user OR b.idDoctor = :id_user)")
    void deleteByUser(@Param("id_user") Integer idUser);
}
