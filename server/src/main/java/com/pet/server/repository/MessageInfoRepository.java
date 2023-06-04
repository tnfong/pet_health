package com.pet.server.repository;

import com.pet.server.entity.MessageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface MessageInfoRepository extends JpaRepository<MessageInfo, Integer> {

    @Query("SELECT m.id AS id " +
            ", m.idGroup AS idGroup " +
            ", m.idUser AS idUser " +
            ", m.content AS content " +
            ", u.fullName AS name " +
            ", m.createdDate AS createdDate " +
            " FROM MessageInfo m " +
            " INNER JOIN User u ON m.idUser = u.id " +
            " WHERE m.idGroup = :id_group " +
            " ORDER BY m.id ASC ")
    List<Map<String, Object>> getByGroup(@Param("id_group") Integer idGroup);

    @Query("SELECT idGroup AS idGroup " +
            ", COUNT(id) AS total " +
            " FROM MessageInfo " +
            " WHERE idGroup IN (SELECT idGroup FROM MessageMember WHERE idUser = :id_user) " +
            " AND idUser != :id_user " +
            " AND read = 0 " +
            " GROUP BY idGroup ")
    List<Map<String, Object>> countMessageNotReadByGroup(@Param("id_user") Integer idUser);

    @Modifying
    @Transactional
    @Query("UPDATE MessageInfo " +
            " SET read = 1 " +
            " WHERE idGroup = :id_group " +
            " AND idUser != :id_user ")
    void updateReadMessage(@Param("id_group") Integer idGroup, @Param("id_user") Integer idUser);
}
