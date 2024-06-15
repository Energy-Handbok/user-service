package com.khaphp.userservice.controller;

import com.khaphp.common.dto.ResponseObject;
import com.khaphp.userservice.Service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {
    private final RedisService redisService;

    @GetMapping("/check")
    public ResponseEntity<Object> checkOTP(String gmail, int otp) {
        ResponseObject<Object> rs = redisService.checkOTP(gmail, otp);
        if(rs.getCode() != 200){
            return ResponseEntity.badRequest().body(rs);
        }
        return ResponseEntity.ok(rs);
    }
//    @PostMapping("/send-otp")
//    public ResponseEntity<?> saveOTPToCacheRedis(@RequestBody SendOTPParam param) {
//        return ResponseEntity.ok(redisService.saveOTPToCacheRedis(param.getGmail(), param.getOtp()));
//    }
}
