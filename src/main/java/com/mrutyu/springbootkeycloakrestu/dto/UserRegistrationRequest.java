package com.mrutyu.springbootkeycloakrestu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    // Getters and setters
}
