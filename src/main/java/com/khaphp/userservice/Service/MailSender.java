package com.khaphp.userservice.Service;


import com.khaphp.common.dto.ResponseObject;

public interface MailSender {
    public ResponseObject<Object> sendOTP(String toEmail);
}
