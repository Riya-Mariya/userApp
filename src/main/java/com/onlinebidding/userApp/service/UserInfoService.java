package com.onlinebidding.userApp.service;

import com.onlinebidding.shared.models.UserInfo;
import com.onlinebidding.userApp.dtos.UserRegistrationDto;

public interface UserInfoService {
    UserInfo registerUserInfo(UserRegistrationDto userRegistrationDto);

    UserInfo getUserInfo(String username);
}
