package com.onlinebidding.userApp.controller;

import com.onlinebidding.shared.DataValidator.DataValidator;
import com.onlinebidding.shared.jwt.JwtGenerator;
import com.onlinebidding.shared.models.UserInfo;
import com.onlinebidding.userApp.dtos.UserLoginDto;
import com.onlinebidding.userApp.service.UserInfoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auction/user")
@Slf4j
public class UserLoginController {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private DataValidator dataValidator;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto, BindingResult result) {
        if (result.hasErrors()) {
            var errorsMap = dataValidator.validateInputData(result);
            return ResponseEntity.badRequest().body(errorsMap);
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userLoginDto.getUserName(), userLoginDto.getPassword()
            ));

            UserInfo userInfo = userInfoService.getUserInfo(userLoginDto.getUserName());
            String jwtToken = jwtGenerator.generateJwtToken(userInfo);

            var response = Map.of("token", jwtToken, "user", userInfo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Exception while login {}", e.getMessage());
            return ResponseEntity.badRequest().body("Bad user name or password");
        }


    }

}
