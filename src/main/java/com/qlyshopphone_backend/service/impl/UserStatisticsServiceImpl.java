package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.model.UserStatistics;
import com.qlyshopphone_backend.repository.UserStatisticsRepository;
import com.qlyshopphone_backend.service.UserStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStatisticsServiceImpl implements UserStatisticsService {
    private final UserStatisticsRepository userStatisticsRepository;
    @Override
    public Optional<UserStatistics> findUserStatisticsByUserId(int userId) {
        return userStatisticsRepository.findByUserUserId(userId);
    }

    @Override
    public void saveUserStatistics(UserStatistics userStatistics) {
        userStatisticsRepository.save(userStatistics);
    }
}
