package com.onlinebidding.userApp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserLoginDto {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
}
