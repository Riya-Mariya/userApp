package com.onlinebidding.userApp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDto {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
    private String role;
    private String emailId;
    private String contactNumber;
}
