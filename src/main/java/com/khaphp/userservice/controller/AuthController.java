package com.khaphp.userservice.controller;

import com.khaphp.userservice.Service.UserSystemService;
import com.khaphp.userservice.dto.LoginParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserSystemService userSystemService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginParam param) {
        return ResponseEntity.ok(userSystemService.login(param));
    }
}
