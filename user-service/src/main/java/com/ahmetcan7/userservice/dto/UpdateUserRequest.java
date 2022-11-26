package com.ahmetcan7.userservice.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
@Getter
public class UpdateUserRequest {
    @NotNull
    private String currentUsername;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String password;
    @NotNull
    private String role;
    @NotNull
    private boolean isNonLocked;
    @NotNull
    private boolean isActive;
    @NotNull
    private MultipartFile profileImage;
}
