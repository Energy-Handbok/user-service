package com.khaphp.userservice.controller;

import com.khaphp.common.dto.ResponseObject;
import com.khaphp.userservice.Service.UserSystemService;
import com.khaphp.userservice.constant.Role;
import com.khaphp.userservice.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/user-system")
//@SecurityRequirement(name = "EnergyHandbook")
@RequiredArgsConstructor
public class UserSystemController {
    private final UserSystemService userSystemService;

    @GetMapping
//    @PreAuthorize("hasAnyRole(" +
//            "'ROLE_"+Role.ADMIN+"'," +
//            "'ROLE_"+Role.EMPLOYEE+"'" +
//            ")")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(defaultValue = "1") int pageIndex){
        ResponseObject<Object> responseObject = userSystemService.getAll(pageSize, pageIndex);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getObject(String id){
        ResponseObject<Object> responseObject = userSystemService.getDetail(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @GetMapping("/detail/{email}")
    public ResponseEntity<?> getObjectByEmail(@PathVariable("email") String email){
        ResponseObject<Object> responseObject = userSystemService.getByEmail(email);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PostMapping("/customer")
    public ResponseEntity<?> createObject(@RequestBody @Valid UserSystemDTOcreate object){
        ResponseObject<Object> responseObject = userSystemService.create(object, Role.CUSTOMER.toString());
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PostMapping("/employee")
    public ResponseEntity<?> createObject2(@RequestBody @Valid UserSystemDTOcreate object){
        ResponseObject<Object> responseObject = userSystemService.create(object, Role.EMPLOYEE.toString());
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PostMapping("/shipper")
    public ResponseEntity<?> createObject3(@RequestBody @Valid UserSystemDTOcreate object){
        ResponseObject<Object> responseObject = userSystemService.create(object, Role.SHIPPER.toString());
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping
    public ResponseEntity<?> updateObject(@RequestBody @Valid UserSystemDTOUpdate object){
        ResponseObject<Object> responseObject = userSystemService.update(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/change-pwd")
    public ResponseEntity<?> changePwd(@RequestBody @Valid ChangePwdParam object){
        ResponseObject<Object> responseObject = userSystemService.changePassword(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/new-pwd")
    @Operation(summary = "for user forgot pwd and verify email successfully")
    public ResponseEntity<?> newPwd(@RequestBody @Valid NewPwdParam object){
        ResponseObject<Object> responseObject = userSystemService.updatePassword(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(@RequestBody @Valid UpdateStatusParam object){
        ResponseObject<Object> responseObject = userSystemService.updateStatus(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestBody @Valid UpdateEmailParam object){
        ResponseObject<Object> responseObject = userSystemService.updateEmail(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping(
            path = "/img",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,     //này là dể nó cho phép swagger upload file
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateImage(@RequestParam("id") String id,
                                         @RequestParam("file") MultipartFile file){
        ResponseObject<Object> responseObject = userSystemService.updateImage(id, file);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteObject(String id){
        ResponseObject<Object> responseObject = userSystemService.delete(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
}
