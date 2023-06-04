package com.pet.server.repository;

import com.pet.server.dto.UserDTO;
import com.pet.server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT u.id AS id" +
            ", u.username AS username" +
            ", u.password AS password" +
            ", u.fullName AS fullName" +
            ", u.phone AS phone" +
            ", u.email AS email" +
            ", u.gender AS gender" +
            ", u.idRole AS idRole" +
            ", u.avatarUrl AS avatarUrl" +
            ", u.createdDate AS createdDate" +
            ", r.name AS roleName " +
            " FROM User u " +
            " INNER JOIN Role r ON u.idRole = r.id " +
            " WHERE (:#{#search.username} = '' OR u.username = :#{#search.username}) " +
            " AND (:#{#search.email} = '' OR u.email = :#{#search.email}) " +
            " AND (:#{#search.phone} = '' OR u.phone = :#{#search.phone}) " +
            " AND (:#{#search.idRole} = 0 OR u.idRole = :#{#search.idRole}) ",
    countQuery = "SELECT COUNT(u.id) " +
            " FROM User u " +
            " INNER JOIN Role r ON u.idRole = r.id " +
            " WHERE (:#{#search.username} = '' OR u.username = :#{#search.username}) " +
            " AND (:#{#search.email} = '' OR u.email = :#{#search.email}) " +
            " AND (:#{#search.phone} = '' OR u.phone = :#{#search.phone}) " +
            " AND (:#{#search.idRole} = '' OR u.idRole = :#{#search.idRole}) ")
    Page<Map<String, Object>> getDataPage(@Param("search") UserDTO search, Pageable pageable);

    @Query("SELECT u.id AS id" +
            ", u.username AS username" +
            ", u.password AS password" +
            ", u.fullName AS fullName" +
            ", u.phone AS phone" +
            ", u.email AS email" +
            ", u.gender AS gender" +
            ", u.idRole AS idRole" +
            ", u.avatarUrl AS avatarUrl" +
            ", u.createdDate AS createdDate" +
            ", r.name AS roleName " +
            " FROM User u " +
            " INNER JOIN Role r ON u.idRole = r.id " +
            " WHERE (:#{#search.username} = '' OR u.username = :#{#search.username}) " +
            " AND (:#{#search.email} = '' OR u.email = :#{#search.email}) " +
            " AND (:#{#search.phone} = '' OR u.phone = :#{#search.phone}) " +
            " AND (:#{#search.idRole} = 0 OR u.idRole = :#{#search.idRole}) ")
    List<Map<String, Object>> getList(@Param("search") UserDTO search);
    User findByUsername(String username);

}
