package com.mrutyu.springbootkeycloakrestu.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private String username;

    private String firstName;
    private String lastName;

    private String password;
    private String email;
    private String role;

}


