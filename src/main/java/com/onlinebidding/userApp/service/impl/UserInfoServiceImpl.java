package com.onlinebidding.userApp.service.impl;


import com.onlinebidding.shared.models.UserInfo;
import com.onlinebidding.shared.repository.UserInfoRepository;
import com.onlinebidding.userApp.dtos.UserRegistrationDto;
import com.onlinebidding.userApp.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo registerUserInfo(UserRegistrationDto userRegistrationDto) {
        var bCryptEncoder = new BCryptPasswordEncoder();
        UserInfo userInfo = UserInfo.builder()
                .userName(userRegistrationDto.getUserName())
                .name(userRegistrationDto.getName())
                .password(bCryptEncoder.encode(userRegistrationDto.getPassword()))
                .role(userRegistrationDto.getRole())
                .contactNumber(userRegistrationDto.getContactNumber())
                .emailId(userRegistrationDto.getEmailId())
                .createdDate(LocalDate.now())
                .build();
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo getUserInfo(String username) {
        return Optional.ofNullable(userInfoRepository.findByUserName(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
