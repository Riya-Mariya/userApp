package com.onlinebidding.userApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinebidding.shared.DataValidator.DataValidator;
import com.onlinebidding.shared.models.UserInfo;
import com.onlinebidding.shared.service.UserDetailsServiceImpl;
import com.onlinebidding.userApp.dtos.UserRegistrationDto;
import com.onlinebidding.userApp.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserLoginController.class)
@ActiveProfiles("test")
public class UserRegistrationControllerTest {

    @MockBean
    private UserInfoService userInfoService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DataValidator dataValidator;
    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @Test
    public void testRegister_ValidUser_ReturnsOkResponse() throws Exception {
        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .userName("Test").password("123213").role("SELLER").build();
        when(dataValidator.validateInputData(any())).thenReturn(null);
        when(userInfoService.registerUserInfo(any())).thenReturn(new UserInfo());

        mockMvc.perform(MockMvcRequestBuilders.post("/auction/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRegistrationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRegister_ValidUser_ReturnsBadResponse() throws Exception {
        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .userName("Test").password("123213").build();
        when(dataValidator.validateInputData(any())).thenReturn(null);
        when(userInfoService.registerUserInfo(any())).thenReturn(new UserInfo());

        mockMvc.perform(MockMvcRequestBuilders.post("/auction/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRegistrationDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
