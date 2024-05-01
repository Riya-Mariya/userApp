package com.onlinebidding.userApp.service;

import com.onlinebidding.shared.models.UserInfo;
import com.onlinebidding.shared.repository.UserInfoRepository;
import com.onlinebidding.userApp.dtos.UserRegistrationDto;
import com.onlinebidding.userApp.service.impl.UserInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserInfoServiceImplTest {

    private final UserInfoRepository userInfoRepository;

    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoServiceImplTest(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoService = new UserInfoServiceImpl(userInfoRepository);

    }

    @Test
    public void testRegisterUserInfo_ValidUser_ReturnsUserInfo() {
        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .userName("testUser").password("password").name("Test User").emailId("test@example.com").role("USER").build();


        UserInfo registeredUser = userInfoService.registerUserInfo(userRegistrationDto);
        assertNotNull(registeredUser.getId());
        assertEquals(userRegistrationDto.getUserName(), registeredUser.getUserName());
        assertEquals(userRegistrationDto.getName(), registeredUser.getName());
        assertEquals(userRegistrationDto.getEmailId(), registeredUser.getEmailId());
        assertNotNull(registeredUser.getCreatedDate());
    }

    @Test
    public void testGetUserInfo_ExistingUser_ReturnsUserInfo() {
        String username = "testUser";
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(username);
        userInfoRepository.save(userInfo);
        UserInfo retrievedUser = userInfoService.getUserInfo(username);
        assertNotNull(retrievedUser);
        assertEquals(userInfo.getUserName(), retrievedUser.getUserName());
    }

    @Test
    public void testGetUserInfo_NonExistingUser_ThrowsException() {
        String username = "testUser2";
        assertThrows(
                UsernameNotFoundException.class,
                () -> userInfoService.getUserInfo(username)
        );
    }
}
