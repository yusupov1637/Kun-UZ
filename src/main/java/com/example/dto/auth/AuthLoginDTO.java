package com.example.dto.auth;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthLoginDTO {
    @Size(min = 4, message = "Login is wrong")
    private String phone;

    @Size(min = 4, message = "Password is wrong")
    private String password;
}
