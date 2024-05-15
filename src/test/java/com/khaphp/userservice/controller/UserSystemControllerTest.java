package com.khaphp.userservice.controller;

import com.khaphp.common.dto.ResponseObject;
import com.khaphp.userservice.Service.UserSystemService;
import com.khaphp.userservice.constant.Role;
import com.khaphp.userservice.dto.*;
import com.khaphp.userservice.entity.UserSystem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserSystemControllerTest {
    @Mock
    private UserSystemService userSystemService;
    @InjectMocks
    private UserSystemController userSystemController;

    private ResponseObject<Object> responseObjectSuccess;
    private ResponseObject<Object> responseObjectFail;
    @Mock
    private MultipartFile multipartFile;
    @BeforeEach
    void setup(){
        responseObjectSuccess = ResponseObject.builder().code(200).message("success").build();
        responseObjectFail = ResponseObject.builder().code(400).message("fail").build();
    }

    @Test
    void getAllSuccess(){
        List<UserSystem> userSystemList = List.of(
                UserSystem.builder().name("a").build(),
                UserSystem.builder().name("b").build()
        );
        responseObjectSuccess.setData(userSystemList);

        when(userSystemService.getAll(anyInt(), anyInt())).thenReturn(responseObjectSuccess);

        assertEquals(responseObjectSuccess, userSystemController.getAll(anyInt(), anyInt()).getBody());
    }

    @Test
    void getAllFail(){
        when(userSystemService.getAll(anyInt(), anyInt())).thenReturn(responseObjectFail);

        assertEquals(responseObjectFail, userSystemController.getAll(anyInt(), anyInt()).getBody());
    }

    @Test
    void getObjectSuccess() {
        when(userSystemService.getDetail(anyString())).thenReturn(responseObjectSuccess);

        assertEquals(responseObjectSuccess, userSystemController.getObject(anyString()).getBody());
    }

    @Test
    void getObjectFail() {
        when(userSystemService.getDetail(anyString())).thenReturn(responseObjectFail);

        assertEquals(responseObjectFail, userSystemController.getObject(anyString()).getBody());
    }

    @Test
    void createObjectSuccess() {
        when(userSystemService.create(any(UserSystemDTOcreate.class), anyString())).thenReturn(responseObjectSuccess);

        assertEquals(responseObjectSuccess, userSystemController.createObject(new UserSystemDTOcreate()).getBody());
        assertEquals(responseObjectSuccess, userSystemController.createObject2(new UserSystemDTOcreate()).getBody());
        assertEquals(responseObjectSuccess, userSystemController.createObject3(new UserSystemDTOcreate()).getBody());
    }

    @Test
    void createObjectFail() {
        when(userSystemService.create(any(UserSystemDTOcreate.class), anyString())).thenReturn(responseObjectFail);

        assertEquals(responseObjectFail, userSystemController.createObject(new UserSystemDTOcreate()).getBody());
        assertEquals(responseObjectFail, userSystemController.createObject2(new UserSystemDTOcreate()).getBody());
        assertEquals(responseObjectFail, userSystemController.createObject3(new UserSystemDTOcreate()).getBody());
    }

    @Test
    void updateObjectSuccess() {
        when(userSystemService.update(any(UserSystemDTOUpdate.class))).thenReturn(responseObjectSuccess);

        assertEquals(responseObjectSuccess, userSystemController.updateObject(new UserSystemDTOUpdate()).getBody());
    }
    @Test
    void updateObjectFail() {
        when(userSystemService.update(any(UserSystemDTOUpdate.class))).thenReturn(responseObjectFail);

        assertEquals(responseObjectFail, userSystemController.updateObject(new UserSystemDTOUpdate()).getBody());
    }

    @Test
    void changePwd_Success() {
        when(userSystemService.changePassword(any(ChangePwdParam.class))).thenReturn(responseObjectSuccess);

        assertEquals(responseObjectSuccess, userSystemController.changePwd(new ChangePwdParam()).getBody());
    }

    @Test
    void changePwd_Fail() {
        when(userSystemService.changePassword(any(ChangePwdParam.class))).thenReturn(responseObjectFail);

        assertEquals(responseObjectFail, userSystemController.changePwd(new ChangePwdParam()).getBody());
    }

    @Test
    void newPwd_Success() {
        when(userSystemService.updatePassword(any(NewPwdParam.class))).thenReturn(responseObjectSuccess);

        assertEquals(responseObjectSuccess, userSystemController.newPwd(new NewPwdParam()).getBody());
    }

    @Test
    void newPwd_Fail() {
        when(userSystemService.updatePassword(any(NewPwdParam.class))).thenReturn(responseObjectFail);

        assertEquals(responseObjectFail, userSystemController.newPwd(new NewPwdParam()).getBody());
    }

    @Test
    void updateStatus_Success() {
        when(userSystemService.updateStatus(any(UpdateStatusParam.class))).thenReturn(responseObjectSuccess);

        assertEquals(responseObjectSuccess, userSystemController.updateStatus(new UpdateStatusParam()).getBody());
    }

    @Test
    void updateStatus_Fail() {
        when(userSystemService.updateStatus(any(UpdateStatusParam.class))).thenReturn(responseObjectFail);

        assertEquals(responseObjectFail, userSystemController.updateStatus(new UpdateStatusParam()).getBody());
    }

    @Test
    void updateEmail_Success() {
        when(userSystemService.updateEmail(any(UpdateEmailParam.class))).thenReturn(responseObjectSuccess);

        assertEquals(responseObjectSuccess, userSystemController.updateEmail(new UpdateEmailParam()).getBody());
    }

    @Test
    void updateEmail_Fail() {
        when(userSystemService.updateEmail(any(UpdateEmailParam.class))).thenReturn(responseObjectFail);

        assertEquals(responseObjectFail, userSystemController.updateEmail(new UpdateEmailParam()).getBody());
    }

    @Test
    void updateImage_Success() {
        when(userSystemService.updateImage(anyString(), any(MultipartFile.class))).thenReturn(responseObjectSuccess);

        assertEquals(responseObjectSuccess, userSystemController.updateImage("1", multipartFile).getBody());
    }

    @Test
    void updateImage_Fail() {
        when(userSystemService.updateImage(anyString(), any(MultipartFile.class))).thenReturn(responseObjectFail);

        assertEquals(responseObjectFail, userSystemController.updateImage("1", multipartFile).getBody());
    }

    @Test
    void deleteObject_Success() {
        when(userSystemService.delete(anyString())).thenReturn(responseObjectSuccess);

        assertEquals(responseObjectSuccess, userSystemController.deleteObject("1").getBody());
    }

    @Test
    void deleteObject_Fail() {
        when(userSystemService.delete(anyString())).thenReturn(responseObjectFail);

        assertEquals(responseObjectFail, userSystemController.deleteObject("1").getBody());
    }
}