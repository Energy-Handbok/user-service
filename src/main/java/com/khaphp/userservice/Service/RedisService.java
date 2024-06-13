package com.khaphp.userservice.Service;


import com.khaphp.common.dto.ResponseObject;

public interface RedisService {
    public ResponseObject<Object> saveOTPToCacheRedis(String key, int otp);
    public ResponseObject<Object> checkOTP(String key, int otp);
}
