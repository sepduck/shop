package com.qlyshopphone_backend.dto.request;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
