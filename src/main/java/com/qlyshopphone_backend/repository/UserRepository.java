package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.dto.response.UserRolesResponse;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.model.enums.Gender;
import com.qlyshopphone_backend.model.enums.Role;
import com.qlyshopphone_backend.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.username = :username")
    Users getUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Optional<Users> getUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM Users u WHERE u.role = :role AND u.email LIKE %:email% ORDER BY u.id DESC")
    List<UserRolesResponse> searchUserByEmail(@Param("role") Role role,
                                            @Param("email") String email);

    @Query("SELECT u FROM Users u WHERE u.role = :role AND u.gender = :gender ORDER BY u.id DESC")
    List<UserRolesResponse> searchUserByGender(@Param("role") Role role,
                                                 @Param("gender") Gender gender);
    
    @Query("SELECT u FROM Users u WHERE u.role = :role ORDER BY u.id DESC")
    List<UserRolesResponse> findUsersByRole(@Param("role") Role role);

    @Query("SELECT u FROM Users u WHERE u.role = :role AND (u.firstName LIKE %:fullName% OR u.lastName LIKE %:fullName%) ORDER BY u.id DESC")
    List<UserRolesResponse> searchEmployeeName(@Param("role") Role role,
                                               @Param("fullName") String fullName);

    @Query("SELECT u FROM Users u WHERE u.role = :role AND u.id = :userId ORDER BY u.id DESC")
    List<UserRolesResponse> searchEmployeeId(@Param("role") Role role,
                                             @Param("userId") Long userId);

    @Query("SELECT u FROM Users u WHERE u.role = :role AND u.phoneNumber LIKE %:phoneNumber% ORDER BY u.id DESC")
    List<UserRolesResponse> searchPhoneNumber(@Param("role") Role role,
                                              @Param("phoneNumber") String phoneNumber);
    
    @Query("SELECT u FROM Users u WHERE u.role = :role AND u.status = :status ORDER BY u.id DESC")
    List<UserRolesResponse> searchUserByStatus(@Param("role") Role role,
                                               @Param("status") Status status);

    @Query("SELECT u FROM Users u WHERE u.id = :userId")
    Users getUserById(@Param("userId") Long userId);
}
