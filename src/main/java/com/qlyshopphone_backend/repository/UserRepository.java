package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query(value = "SELECT u.* , u.user_id AS id, r.role_name, g.gender_name FROM users u INNER JOIN users_roles ur ON u.user_id = ur.user_id INNER JOIN roles r ON ur.role_id = r.role_id INNER JOIN gender g on u.gender_id = g.gender_id WHERE u.delete_user = false", nativeQuery = true)
    List<Map<String, Object>> getAllUsers();

    Users findByUsername(String username);

    @Query("SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = false AND u.delete_user = false ORDER BY id DESC")
    List<Map<String, Object>> getCustomer();

    @Query("SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = false AND u.delete_user = false AND u.fullName LIKE %?1% ORDER BY id DESC")
    List<Map<String, Object>> searchCustomerName(String fullName);

    @Query("SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = false AND u.delete_user = false AND u.userId = :userId ORDER BY id DESC")
    List<Map<String, Object>> searchCustomerId(Long userId);

    @Query("SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = false AND u.delete_user = false AND u.phoneNumber LIKE %?1% ORDER BY id DESC")
    List<Map<String, Object>> searchByPhoneNumber(String phoneNumber);

    @Query("SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = false AND u.delete_user = false AND u.email LIKE %?1% ORDER BY id DESC")
    List<Map<String, Object>> searchByEmail(String email);

    @Query("SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = false AND u.delete_user = false AND u.address LIKE %?1% ORDER BY id DESC")
    List<Map<String, Object>> searchByAddress(String address);

    @Query("SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = false AND u.delete_user = false ORDER BY id DESC")
    List<Map<String, Object>> searchByActive(byte active);

    @Query("SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = false AND u.delete_user = false AND u.gender.genderId = 1 ORDER BY id DESC")
    List<Map<String, Object>> searchByGenderId1();

    @Query("SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = false AND u.delete_user = false AND u.gender.genderId = 2 ORDER BY id DESC")
    List<Map<String, Object>> searchByGenderId2();

    @Query("SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = false AND u.delete_user = false AND u.gender.genderId = 3 ORDER BY id DESC")
    List<Map<String, Object>> searchByGenderId3();

    @Modifying
    @Query("UPDATE Users u SET u.delete_user = TRUE WHERE u.employee = FALSE AND u.userId = :userId")
    void deleteCustomerId(Long userId);

    @Query("SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = true AND u.delete_user = false ORDER BY id DESC")
    List<Map<String, Object>> getEmployeeList();

    @Query(value = "SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = true AND u.delete_user = false AND u.fullName LIKE %?1% ORDER BY id DESC")
    List<Map<String, Object>> searchEmployeeName(@Param("full_name") String fullName);

    @Query(value = "SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = true AND u.delete_user = false AND u.userId = :userId ORDER BY id DESC")
    List<Map<String, Object>> searchEmployeeId(Long userId);

    @Query(value = "SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = true AND u.delete_user = false AND u.phoneNumber LIKE %?1% ORDER BY id DESC")
    List<Map<String, Object>> searchPhoneNumber(String phoneNumber);

    @Query("SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = true AND u.delete_user = true ORDER BY id DESC")
    List<Map<String, Object>> searchByNoActive();

    @Query(value = "SELECT u, u.userId AS id, g.genderName FROM Users u LEFT JOIN Gender g on u.gender.genderId = g.genderId WHERE u.employee = true ORDER BY id DESC")
    List<Map<String, Object>> searchByAllActive();

    @Modifying
    @Query("UPDATE Users u SET u.delete_user = TRUE WHERE u.employee = TRUE AND u.userId = :userId")
    void deleteEmployeeById(Long userId);


}
