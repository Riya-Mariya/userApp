package com.onlinebidding.userApp.controller;

import com.onlinebidding.shared.DataValidator.DataValidator;
import com.onlinebidding.shared.models.UserInfo;
import com.onlinebidding.userApp.dtos.UserRegistrationDto;
import com.onlinebidding.userApp.service.UserInfoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auction/user")
@Slf4j
public class UserRegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private DataValidator dataValidator;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserRegistrationDto userRegistrationDto, BindingResult result) {
        if (result.hasErrors()) {
            var errorsMap = dataValidator.validateInputData(result);
            return ResponseEntity.badRequest().body(errorsMap);
        }

        try {
            UserInfo userInfo = userInfoService.registerUserInfo(userRegistrationDto);
            return ResponseEntity.ok(userInfo);
        } catch(DataIntegrityViolationException ex){
            logger.error("UserName already exists {}", ex.getMessage());
            return ResponseEntity.badRequest().body("UserName already exist. Please choose another one");

        }
        catch (Exception e) {
            logger.error("Exception while registering the user {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid user details");
        }


    }

}
