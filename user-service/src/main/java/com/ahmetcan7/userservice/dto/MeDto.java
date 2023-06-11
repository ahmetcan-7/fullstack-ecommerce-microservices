package com.ahmetcan7.userservice.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class MeDto {
    private String userId;
    private List<String> roles;
    private String email;
    private String firstName;
    private String lastName;
    private String profileImageURL;
}
