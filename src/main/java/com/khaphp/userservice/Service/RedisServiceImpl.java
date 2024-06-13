package com.khaphp.userservice.Service;

import com.khaphp.common.dto.ResponseObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    public static final String EXCEPTION = "Exception: ";
    private final Environment env;
    private final RedisTemplate redisTemplate;
    @Override
    public ResponseObject<Object> saveOTPToCacheRedis(String gmail, int otp) {
        try {
            int TimeToLive = Integer.parseInt(env.getProperty("time_minutes_expired_otp", "2"));

            redisTemplate.opsForValue().set(gmail, otp);
            redisTemplate.expire(gmail, TimeToLive, TimeUnit.MINUTES);   //set key = gmail, value = otp và có expired là ? minutes
            log.info("Save otp of " + gmail + " : " + otp + " to redis cache with time to live is " + TimeToLive + " minute");
            return ResponseObject.builder()
                    .code(200)
                    .message("save OTP success")
                    .build();

        } catch (Exception ex) {
            return ResponseObject.builder()
                    .code(400)
                    .message(EXCEPTION + ex.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> checkOTP(String key, int otp) {
        try {
            int otp1 = (Integer) redisTemplate.opsForValue().get(key);
            if (otp1 == otp) {
                //xóa opt đi nếu còn thời gian
                redisTemplate.opsForValue().getAndDelete(key);
                return ResponseObject.builder().code(200).message("OK, correct OTP !!").build();
            } else {
                return ResponseObject.builder().code(400).message("Wrong OTP !!").build();
            }
        } catch (NullPointerException ex) {
            return ResponseObject.builder().code(400).message("your OTP is expired !!").build();
        } catch (Exception ex) {
            return ResponseObject.builder().code(400).message(EXCEPTION + ex.getMessage()).build();
        }
    }
}
