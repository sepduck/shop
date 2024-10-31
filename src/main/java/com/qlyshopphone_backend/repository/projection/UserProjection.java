package com.qlyshopphone_backend.repository.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface UserProjection {
    Long getId();

    String getUsername();

    String getPassword();

    String getPhoneNumber();

    LocalDate getStartDay();

    String getIdCard();

    String getGender();

    String getFacebook();

    String getEmail();

    String getFirstName();

    String getLastName();

    String getStatus();

    LocalDate getBirthday();

    String getAddress();
}
