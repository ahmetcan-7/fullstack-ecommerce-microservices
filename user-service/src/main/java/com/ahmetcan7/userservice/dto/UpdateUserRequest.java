package com.ahmetcan7.userservice.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateUserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
    private boolean isNonLocked;
    private boolean isActive;
    private MultipartFile profileImage;
}
