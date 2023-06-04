package com.pet.server.repository;

import com.pet.server.entity.MessageGroup;
import com.pet.server.entity.MessageMember;
import org.aspectj.bridge.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface MessageMemberRepository extends JpaRepository<MessageMember, Integer> {

    @Query("SELECT idGroup AS idGroup, COUNT(id) AS total " +
            "FROM MessageMember " +
            "WHERE idUser = :id_u1 OR idUser = :id_u2 " +
            "GROUP BY idGroup " +
            "HAVING COUNT(id) > 1")
    List<Map<String, Object>> checkGroupByUser(@Param("id_u1") Integer idUser1, @Param("id_u2") Integer idUser2);

    List<MessageMember> findByIdGroup(Integer idGroup);
}
