package com.ahmetcan7.userservice.dto;

import com.ahmetcan7.userservice.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private String accessToken;
    private String role;
}
