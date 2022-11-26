package com.ahmetcan7.userservice.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class LoginUserRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}