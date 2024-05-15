package com.khaphp.userservice.dto;

import com.khaphp.userservice.util.validdata.birthday.ValidBirthday;
import com.khaphp.userservice.util.validdata.gender.ValidGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSystemDTOcreate {
    @Size(min = 5, message = "name length must be at least 5 characters")
    @Size(max = 40, message = "name length must be at most 40 characters")
    private String name;
    @Size(min = 5, message = "Username length must be at least 5 characters")
    @Size(max = 40, message = "Username length must be at most 40 characters")
    private String username;
    @Size(min = 5, message = "password length must be at least 5 characters")
    @Size(max = 20, message = "password length must be at most 20 characters")
    private String password;
    @Email
    private String email;
    @ValidBirthday
    private long birthdayL;
    @ValidGender
    private String gender;
//    private String status;
//    private String role;
//    private boolean premium;
}
