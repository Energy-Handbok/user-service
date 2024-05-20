package com.khaphp.userservice.Service;

import com.khaphp.common.dto.ResponseObject;
import com.khaphp.userservice.constant.Status;
import com.khaphp.userservice.dto.*;
import com.khaphp.userservice.entity.UserSystem;
import com.khaphp.userservice.exception.UserNotFoudException;
import com.khaphp.userservice.repository.UserSystemRepository;
import com.khaphp.userservice.util.security.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserSystemServiceImpl implements UserSystemService {
    public static final String SUCCESS_MESSAGE = "Success";
    public static final String USER_NOT_FOUND_MESSGAE = "user not found";
    public static final String EXCEPTION_FORMAT_MESSAGE = "Exception: ";
    private final ModelMapper modelMapper;

    private final FileStore fileStore;

    @Value("${aws.s3.link_bucket}")
    private String linkBucket;

    private final UserSystemRepository userRepository;

    private final JwtHelper jwtHelper;

//    @Autowired
//    private WalletService walletService;


    @Value("${logo.energy_handbook.name}")
    private String logoName;


//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//            }
//        };
//    }

    @Override
    public ResponseObject<Object> getAll(int pageSize, int pageIndex) {
        Page<UserSystem> objListPage = null;
        List<UserSystem> objList = null;
        int totalPage = 0;
        //paging
        if (pageSize > 0 && pageIndex > 0) {
            objListPage = userRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));  //vì current page ở code nó start = 0, hay bên ngoài la 2pga đầu tiên hay 1
            if (objListPage != null) {
                totalPage = objListPage.getTotalPages();
                objList = objListPage.getContent();
            }
        } else { //get all
            objList = userRepository.findAll();
            pageIndex = 1;
        }
        objList.forEach(userSystem -> userSystem.setImgUrl(linkBucket + userSystem.getImgUrl()));
        return ResponseObject.builder()
                .code(200).message(SUCCESS_MESSAGE)
                .pageSize(objList.size()).pageIndex(pageIndex).totalPage(totalPage)
                .data(objList)
                .build();
    }

    @Override
    public ResponseObject<Object> getDetail(String id) {
        try {
            UserSystem userSystem = userRepository.findById(id).orElse(null);
            if (userSystem == null) {
                throw new UserNotFoudException(USER_NOT_FOUND_MESSGAE);
            }
            try {
                if (userSystem.getImgUrl() != null) {
                    userSystem.setImgUrl(linkBucket + userSystem.getImgUrl());
                }
            } catch (Exception e) {

            }

//            userSystem.setImgUrl(linkBucket + userSystem.getImgUrl());
            return ResponseObject.builder()
                    .code(200)
                    .message("Found")
                    .data(userSystem)
                    .build();
        } catch (Exception e) {
            return ResponseObject.builder()
                    .code(400)
                    .message(EXCEPTION_FORMAT_MESSAGE + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> getByEmail(String email) {
        try {
            UserSystem userSystem = userRepository.findByEmail(email);
            if (userSystem == null) {
                throw new UserNotFoudException(USER_NOT_FOUND_MESSGAE);
            }
            try {
                if (userSystem.getImgUrl() != null) {
                    userSystem.setImgUrl(linkBucket + userSystem.getImgUrl());
                }
            } catch (Exception e) {

            }

//            userSystem.setImgUrl(linkBucket + userSystem.getImgUrl());
            return ResponseObject.builder()
                    .code(200)
                    .message("Found")
                    .data(userSystem)
                    .build();
        } catch (Exception e) {
            return ResponseObject.builder()
                    .code(400)
                    .message(EXCEPTION_FORMAT_MESSAGE + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> create(UserSystemDTOcreate object, String role) {
        try {
            //check unique
            if (userRepository.existsByUsername(object.getUsername())) {
                throw new Exception("username exists");
            }
            if (userRepository.existsByEmail(object.getEmail())) {
                throw new Exception("email exists");
            }

            UserSystem userSystem = modelMapper.map(object, UserSystem.class);
            userSystem.setStatus(Status.ACTIVE.toString());
            userSystem.setRole(role);
            userSystem.setPremium(false);
            userSystem.setBirthday(new Date(object.getBirthdayL() * 1000));
            userSystem.setImgUrl(logoName);
            userSystem = userRepository.save(userSystem);

            //create wallet for customer
//            if (role.equals(Role.CUSTOMER.toString())) {
//                walletService.create(WalletDTOcreate.builder().customerId(userSystem.getId()).build(), this);
//            }

            return ResponseObject.builder()
                    .code(200)
                    .message(SUCCESS_MESSAGE)
                    .data(userSystem)
                    .build();
        } catch (Exception e) {
            return ResponseObject.builder()
                    .code(400)
                    .message(EXCEPTION_FORMAT_MESSAGE + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> update(UserSystemDTOUpdate object) {
        try {
            UserSystem userSystem = userRepository.findById(object.getId()).orElse(null);
            if (userSystem == null) {
                throw new UserNotFoudException(USER_NOT_FOUND_MESSGAE);
            }
            userSystem.setName(object.getName());
            userSystem.setBirthday(new Date(object.getBirthdayL() * 1000));
            userSystem.setGender(object.getGender());
            userRepository.save(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message(SUCCESS_MESSAGE)
                    .data(userSystem)
                    .build();
        } catch (Exception e) {
            return ResponseObject.builder()
                    .code(400)
                    .message(EXCEPTION_FORMAT_MESSAGE + e.getMessage())
                    .build();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject<Object> updateImage(String id, MultipartFile file) {
        try {
            //find user
            UserSystem userSystem = userRepository.findById(id).orElse(null);
            if (userSystem == null) {
                throw new UserNotFoudException(USER_NOT_FOUND_MESSGAE);
            }
            if (!userSystem.getImgUrl().equals(logoName)) {
                fileStore.deleteImage(userSystem.getImgUrl());
            }
            userSystem.setImgUrl(fileStore.uploadImg(file));
            userRepository.save(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message(SUCCESS_MESSAGE)
                    .build();
        } catch (Exception e) {
            return ResponseObject.builder()
                    .code(400)
                    .message(EXCEPTION_FORMAT_MESSAGE + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> delete(String id) {
        try {
            UserSystem userSystem = userRepository.findById(id).orElse(null);
            if (userSystem == null) {
                throw new UserNotFoudException(USER_NOT_FOUND_MESSGAE);
            }
            if (!userSystem.getImgUrl().equals(logoName)) {
                fileStore.deleteImage(userSystem.getImgUrl());
            }
            //delete wallet
//            walletService.delete(userSystem.getWallet().getId());
            userRepository.delete(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message(SUCCESS_MESSAGE)
                    .data(null)
                    .build();
        } catch (Exception e) {
            return ResponseObject.builder()
                    .code(400)
                    .message(EXCEPTION_FORMAT_MESSAGE + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> login(LoginParam param) {
        try {
            UserSystem userSystem = userRepository.findByUsernameAndPassword(param.getUsername(), param.getPassword());
            if (userSystem == null) {
                throw new Exception("Wrong username or password");
            }
            if (userSystem.getStatus().equals(Status.DEACTIVE.toString())) {
                throw new Exception("Your account was Ban");
            }
            return ResponseObject.builder()
                    .code(200)
                    .message(SUCCESS_MESSAGE)
                    .data(jwtHelper.generateToken(userSystem.getUsername(), Map.of("role", userSystem.getRole())))
                    .build();
        } catch (Exception e) {
            return ResponseObject.builder()
                    .code(400)
                    .message(EXCEPTION_FORMAT_MESSAGE + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> changePassword(ChangePwdParam param) {
        try {
            UserSystem userSystem = userRepository.findById(param.getId()).orElse(null);
            if (userSystem == null) {
                throw new UserNotFoudException(USER_NOT_FOUND_MESSGAE);
            }
            if (!userSystem.getPassword().equals(param.getPassword())) {
                throw new Exception("Wrong old password");
            }
            userSystem.setPassword(param.getNewPassword());
            userRepository.save(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message(SUCCESS_MESSAGE)
                    .build();
        } catch (Exception e) {
            return ResponseObject.builder()
                    .code(400)
                    .message(EXCEPTION_FORMAT_MESSAGE + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> updateStatus(UpdateStatusParam param) {
        try {
            UserSystem userSystem = userRepository.findById(param.getId()).orElse(null);
            if (userSystem == null) {
                throw new UserNotFoudException(USER_NOT_FOUND_MESSGAE);
            }
            userSystem.setStatus(param.getStatus());
            userRepository.save(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message(SUCCESS_MESSAGE)
                    .build();
        } catch (Exception e) {
            return ResponseObject.builder()
                    .code(400)
                    .message(EXCEPTION_FORMAT_MESSAGE + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> updateEmail(UpdateEmailParam param) {
        try {
            UserSystem userSystem = userRepository.findById(param.getId()).orElse(null);
            if (userSystem == null) {
                throw new UserNotFoudException(USER_NOT_FOUND_MESSGAE);
            }
            userSystem.setEmail(param.getEmail());
            userRepository.save(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message(SUCCESS_MESSAGE)
                    .build();
        } catch (Exception e) {
            ResponseObject<Object> responseObject = ResponseObject.builder().code(400).build();
            if(e.getMessage().contains("Duplicate entry")){
                responseObject.setMessage("Email đã được sử dụng");
            }else{
                responseObject.setMessage(EXCEPTION_FORMAT_MESSAGE + e.getMessage());
            }
            return responseObject;
        }
    }

    @Override
    public ResponseObject<Object> updatePassword(NewPwdParam param) {
        try {
            UserSystem userSystem = userRepository.findById(param.getId()).orElse(null);
            if (userSystem == null) {
                throw new UserNotFoudException(USER_NOT_FOUND_MESSGAE);
            }
            userSystem.setPassword(param.getNewPassword());
            userRepository.save(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message(SUCCESS_MESSAGE)
                    .build();
        } catch (Exception e) {
            return ResponseObject.builder()
                    .code(400)
                    .message(EXCEPTION_FORMAT_MESSAGE + e.getMessage())
                    .build();
        }
    }
}
