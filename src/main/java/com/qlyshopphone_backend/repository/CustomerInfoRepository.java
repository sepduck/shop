package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.CustomerInfo;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.projection.CustomerInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {
    @Query("""
            SELECT ci FROM CustomerInfo ci
            WHERE ci.users.id = :userId
            AND ci.id = :customerInfoId
            AND ci.status = :activeStatus
            """)
    Optional<CustomerInfo> findCustomerInfoByUserIdAndId(@Param("userId") Long userId,
                                                         @Param("customerInfoId") Long customerInfoId,
                                                         @Param("activeStatus") Status activeStatus);

    @Query("""
            SELECT ci FROM CustomerInfo ci
             WHERE ci.users.id = :userId
             AND ci.status = :activeStatus
            """)
    List<CustomerInfoProjection> findCustomerInfoByUser(@Param("userId") Long userId,
                                                        @Param("activeStatus") Status activeStatus);

    @Query("""
            SELECT ci FROM CustomerInfo ci
            WHERE ci.users.id = :userId
            AND ci.id = :customerInfoId
            AND ci.status = :activeStatus
            """)
    CustomerInfoProjection findCustomerInfoByIdFromUser(@Param("userId") Long userId,
                                              @Param("customerInfoId") Long customerInfoId,
                                              @Param("activeStatus") Status activeStatus);

    @Query("""
            SELECT COUNT (ci)
            FROM CustomerInfo ci
            WHERE ci.users.id = :userId
            """)
    long countByUserId(Long userId);

}
