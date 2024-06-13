package com.khaphp.userservice.controller;

import com.khaphp.userservice.Service.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailSender mailSender;

    @GetMapping("/send-otp")
    public ResponseEntity<Object> sendOTP(@RequestParam String toEmail) {
        return ResponseEntity.ok(mailSender.sendOTP(toEmail));
    }
}
