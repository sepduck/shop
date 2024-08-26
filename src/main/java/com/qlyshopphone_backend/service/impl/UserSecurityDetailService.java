package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.model.UserSecurityDetails;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username);
        if (users == null) {
            throw new RuntimeException("Account does not exist");
        }
        UserSecurityDetails userSecurityDetails = new UserSecurityDetails(users);
        return userSecurityDetails;
    }
}