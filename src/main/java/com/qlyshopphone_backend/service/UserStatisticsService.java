package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.model.UserStatistics;

import java.util.Optional;

public interface UserStatisticsService {
    Optional<UserStatistics> findUserStatisticsByUserId(int userId);

    void saveUserStatistics(UserStatistics userStatistics);
}
