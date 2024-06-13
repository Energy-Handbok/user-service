package com.khaphp.userservice.Service;

import com.khaphp.common.dto.ResponseObject;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {
    private final Environment env;
    private final JavaMailSender mailSender;
    private final RedisService redisService;

    @Override
    public ResponseObject<Object> sendOTP(String toEmail) {
        try {
            //create otp
            Random random = new Random();
            int otp = random.nextInt(900000) + 100000;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setSubject("OTP for Energy Handbook");
            helper.setFrom("Energy-Handbook");
            helper.setTo(toEmail);

            boolean html = true;
            helper.setText("<b>Dear Customer</b>," +
                    "<br><br>Your register OTP is: <b>" + otp + "</b>" +
                    "<br><br>Thank you for using Energy Handbook." +
                    "<br>Best regards," +
                    "<br><b>Energy Handbook</b>" +
                    "<br><img src='" + env.getProperty("logo.energy_handbook.url") + "'/>", html);
            mailSender.send(message);

            redisService.saveOTPToCacheRedis(toEmail, otp);
            log.info("Send otp of " + toEmail + " : " + otp);

            return ResponseObject.builder()
                    .code(200)
                    .message("send OTP email success")
                    .build();

        } catch (Exception ex) {
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + ex.getMessage())
                    .build();
        }
    }
}
