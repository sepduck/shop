package com.qlyshopphone_backend.service.util;

import com.qlyshopphone_backend.exceptions.ApiRequestException;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceHelper {
    private final EntityFinder entityFinder;

    public void checkAdminRole() {
        Users currentUser = entityFinder.getCurrentAuthenticatedUser();
        if (currentUser.getRole().equals(Role.ADMIN)) {
            throw new ApiRequestException("Bạn không có quyền thực hiện hành động này.", HttpStatus.UNAUTHORIZED);
        }
    }
}
