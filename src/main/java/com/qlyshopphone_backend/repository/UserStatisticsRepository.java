package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {
    @Query("""
            SELECT us
            FROM UserStatistics us
            WHERE us.users.id = :userId
            """)
    Optional<UserStatistics> findByUsersId(@Param("userId") Long userId);
}
