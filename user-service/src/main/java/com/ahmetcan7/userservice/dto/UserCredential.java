package com.ahmetcan7.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserCredential {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
