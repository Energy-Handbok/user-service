package com.khaphp.userservice.config;

import com.khaphp.common.constant.EmailDefault;
import com.khaphp.userservice.Service.UserSystemService;
import com.khaphp.userservice.constant.Gender;
import com.khaphp.userservice.constant.Role;
import com.khaphp.userservice.dto.UserSystemDTOcreate;
import com.khaphp.userservice.entity.UserSystem;
import com.khaphp.userservice.repository.UserSystemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitDefaultData {
    private final UserSystemRepository userRepository;
    private final UserSystemService userService;
    @Bean
    public CommandLineRunner commandLineRunner(){
        return args -> {
//default account
            UserSystem userSystem = userRepository.findByEmail(EmailDefault.CUSTOMER_EMAIL_DEFAULT);
            if(userSystem == null){
                userService.create(UserSystemDTOcreate.builder()
                        .name("Default Customer")
                        .email(EmailDefault.CUSTOMER_EMAIL_DEFAULT)
                        .gender(Gender.MALE.toString())
                        .password(UUID.randomUUID().toString())
                        .username("customer")
                        .build(), Role.CUSTOMER.toString());
            }

            userSystem = null;
            userSystem = userRepository.findByEmail(EmailDefault.EMPLOYEE_EMAIL_DEFAULT);
            if(userSystem == null){
                userService.create(UserSystemDTOcreate.builder()
                        .name("Default Employee")
                        .email(EmailDefault.EMPLOYEE_EMAIL_DEFAULT)
                        .gender(Gender.MALE.toString())
                        .password(UUID.randomUUID().toString())
                        .username("employee")
                        .build(), Role.EMPLOYEE.toString());
            }

            userSystem = null;
            userSystem = userRepository.findByEmail(EmailDefault.SHIPPER_EMAIL_DEFAULT);
            if(userSystem == null){
                userService.create(UserSystemDTOcreate.builder()
                        .name("Default Shipper")
                        .email(EmailDefault.SHIPPER_EMAIL_DEFAULT)
                        .gender(Gender.MALE.toString())
                        .password(UUID.randomUUID().toString())
                        .username("shipper")
                        .build(), Role.SHIPPER.toString());
            }

            userSystem = null;
            userSystem = userRepository.findByEmail(EmailDefault.ADMIN_EMAIL_DEFAULT);
            if(userSystem == null){
                userService.create(UserSystemDTOcreate.builder()
                        .name("Admintrator")
                        .email(EmailDefault.ADMIN_EMAIL_DEFAULT)
                        .gender(Gender.MALE.toString())
                        .password("11111")
                        .username("admin")
                        .build(), Role.ADMIN.toString());
            }
        };
    }
}
