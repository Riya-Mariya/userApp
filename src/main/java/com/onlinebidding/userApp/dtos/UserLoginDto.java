package com.onlinebidding.userApp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserLoginDto {
    @NotEmpty(message = "Username cannot be empty")
    private String userName;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
