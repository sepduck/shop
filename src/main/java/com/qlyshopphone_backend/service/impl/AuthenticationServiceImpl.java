package com.qlyshopphone_backend.service.impl;
import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.dto.UsersDTO;
import com.qlyshopphone_backend.model.Gender;
import com.qlyshopphone_backend.model.Roles;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.repository.GenderRepository;
import com.qlyshopphone_backend.repository.RoleRepository;
import com.qlyshopphone_backend.repository.UserRepository;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtProvider provider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenderRepository genderRepository;

    @Override
    public String login(Users users) {
           Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                                   (users.getUsername(), users.getPassword()));
           SecurityContextHolder.getContext().setAuthentication(authentication);
        return provider.generateToken(authentication);
    }

    @Override
    public String register(UsersDTO usersDTO) throws Exception {
        Gender gender = genderRepository.findById(usersDTO.getGenderId())
                .orElseThrow(() -> new RuntimeException(GENDER_NOT_FOUND));
        Users users = new Users();
        users.setUsername(usersDTO.getUsername());
        users.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
        users.setPhoneNumber(usersDTO.getPhoneNumber());
        users.setStartDay(LocalDate.now());
        users.setIdCard(usersDTO.getIdCard());
        users.setGender(gender);
        users.setFacebook(usersDTO.getFacebook());
        users.setEmail(usersDTO.getEmail());
        users.setAddress(usersDTO.getAddress());
        users.setDelete_user(usersDTO.isDeleteUser());
        users.setEmployee(usersDTO.isEmployee());
        users.setFullName(usersDTO.getFullName());
        users.setBirthday(usersDTO.getBirthday());
        users.setFileUser(usersDTO.getFileUser().getBytes());

        Roles roles = roleRepository.findById(usersDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
        users.setRoles(List.of(roles));
        userRepository.save(users);
        return YOU_HAVE_REGISTER_SUCCESSFULLY;
    }

    @Override
    public Users getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }
}
