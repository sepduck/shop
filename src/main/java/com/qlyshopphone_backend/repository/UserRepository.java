package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query(value = "SELECT u.*, u.user_id AS id, r.role_name, g.gender_name\n" +
            "FROM users u\n" +
            "         INNER JOIN users_roles ur ON u.user_id = ur.user_id\n" +
            "         INNER JOIN roles r ON ur.role_id = r.role_id\n" +
            "         INNER JOIN gender g on u.gender_id = g.gender_id\n" +
            "WHERE u.delete_user = false;", nativeQuery = true)
    List<Map<String, Object>> getAllUsers();

    Users findByUsername(String username);
}
