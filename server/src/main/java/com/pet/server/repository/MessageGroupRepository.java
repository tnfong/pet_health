package com.pet.server.repository;

import com.pet.server.entity.MessageGroup;
import com.pet.server.entity.MessageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface MessageGroupRepository extends JpaRepository<MessageGroup, Integer> {

    @Query("SELECT mg.id AS id " +
            " , u.fullName AS name " +
            " , u.id AS idUser " +
            " , u.avatarUrl AS avatarUrl " +
            " , mi.idUser AS idFrom " +
            " , mi.createdDate AS createdDate " +
            " , mi.content AS content " +
            " , mi.read AS read " +
            " , 0 AS totalUnRead " +
            " FROM User u " +
            " LEFT JOIN MessageMember mm ON u.id = mm.idUser " +
            " AND mm.idGroup IN (" +
            "   SELECT idGroup FROM MessageMember WHERE idUser = :id_user" +
            " ) " +
            " LEFT JOIN MessageGroup mg ON mm.idGroup = mg.id " +
            " LEFT JOIN MessageInfo mi ON mg.id = mi.idGroup AND mi.id IN (SELECT MAX(id) FROM MessageInfo GROUP BY idGroup) " +
            " WHERE u.id != :id_user " +
            " ORDER BY mi.createdDate DESC ")
    List<Map<String, Object>> getMyList(@Param("id_user") Integer idUser);


}
