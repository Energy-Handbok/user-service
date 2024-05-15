package com.khaphp.userservice.controller;

import com.khaphp.common.dto.ResponseObject;
import com.khaphp.userservice.Service.UserSystemService;
import com.khaphp.userservice.dto.LoginParam;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    @Mock
    private UserSystemService userSystemService;

    @InjectMocks
    private AuthController authController;
    @Test
    void login() {
        when(userSystemService.login(any(LoginParam.class))).thenReturn(ResponseObject.builder().data("token").build());
        assertNotNull(authController.login(any(LoginParam.class)));
    }
}