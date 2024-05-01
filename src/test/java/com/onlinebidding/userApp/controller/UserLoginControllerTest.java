package com.onlinebidding.userApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinebidding.shared.DataValidator.DataValidator;
import com.onlinebidding.shared.jwt.JwtGenerator;
import com.onlinebidding.shared.models.UserInfo;
import com.onlinebidding.shared.service.UserDetailsServiceImpl;
import com.onlinebidding.userApp.dtos.UserLoginDto;
import com.onlinebidding.userApp.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(UserLoginController.class)
@ActiveProfiles("test")
public class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserInfoService userInfoService;

    @MockBean
    private JwtGenerator jwtGenerator;

    @MockBean
    private DataValidator dataValidator;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @Test
    public void testLoginSuccess() throws Exception {
        UserLoginDto userLoginDto = new UserLoginDto("username", "password");
        UserInfo userInfo = UserInfo.builder().userName("test").password("test123").build();

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUserName(), userLoginDto.getPassword())))
                .thenReturn(null);
        when(userInfoService.getUserInfo(userLoginDto.getUserName())).thenReturn(userInfo);
        when(jwtGenerator.generateJwtToken(userInfo)).thenReturn("token");

        mockMvc.perform(MockMvcRequestBuilders.post("/auction/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userLoginDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    @Test
    public void testLoginFailure() throws Exception {
        UserLoginDto userLoginDto = new UserLoginDto("username", "password");

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUserName(), userLoginDto.getPassword())))
                .thenThrow(new RuntimeException("Invalid credentials"));

        mockMvc.perform(MockMvcRequestBuilders.post("/auction/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userLoginDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Bad user name or password"));
    }
}
