package com.qlyshopphone_backend.repository.projection;

import java.time.LocalDate;

public interface AuthUserProjection {
    Long getUserId();

    String getUsername();

    String getPassword();

    String getPhoneNumber();

    LocalDate getStartDay();

    String getIdCard();

    String getGender();

    String getFacebook();

    String getEmail();

    String getAddress();

    String getFirstName();

    String getLastName();

    String getStatus();

    LocalDate getBirthday();
}
