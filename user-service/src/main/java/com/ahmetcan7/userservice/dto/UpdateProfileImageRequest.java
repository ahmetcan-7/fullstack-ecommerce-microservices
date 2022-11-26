package com.ahmetcan7.userservice.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
public class UpdateProfileImageRequest {
    @NotNull
    private String username;
    @NotNull
    private MultipartFile profileImage;
}
