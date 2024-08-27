package com.qlyshopphone_backend.dto;
import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import lombok.Data;

@Data
public class PasswordChangeRequestDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
