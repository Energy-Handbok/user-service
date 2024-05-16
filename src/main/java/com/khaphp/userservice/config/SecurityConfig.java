//package com.khaphp.userservice.config;
//
//import com.khaphp.userservice.util.security.CustomAccessDeniedHandler;
//import com.khaphp.userservice.util.security.CustomAuthenticationEntryPoint;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.List;
//
//import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final String[] PUBLISH_ENDPOINTS = {
//            "/swagger-ui/**",
//            "/v3/api-docs/**",
//            "/auth/login",
//            "/help/**",
//            "/api/v1/user-system/detail",
//            "/api/v1/user-system/customer",
//            "/api/v1/user-system/employee",
//            "/api/v1/user-system/shipper",
//    };
//
//    private final List<String> allowOrigin = List.of(
//            "http://localhost:8080",
//            "http://localhost:8081",
//            "http://localhost:8082",
//            "http://localhost:8083",
//            "http://localhost:8084",
//            "http://localhost:8085",
//            "http://localhost:8086",
//            "http://localhost:8087",
//            "http://localhost:8088",
//            "http://localhost:8089",
//            "http://localhost:8761",
//            "http://localhost:9411"
//            );
//
//    private final UserDetailsService userDetailsService;
//
//    /**
//     * có thể thêm: http.csrf(x -> x.disable());
//     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable);
//
//        http.sessionManagement(x -> x.sessionCreationPolicy(STATELESS));
//
//        http.authorizeHttpRequests(x -> x
//                .requestMatchers(PUBLISH_ENDPOINTS).permitAll()
//                .anyRequest().authenticated()
//        );
//
//        http.exceptionHandling(x -> x
//                .accessDeniedHandler(new CustomAccessDeniedHandler())
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//        );
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationProvider getAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        return daoAuthenticationProvider;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    //cấu hình cros cho phép gọi từ ng lạ
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configurationSource = new CorsConfiguration();
//        // Cho phép các nguồn gốc (origins) truy cập
//        configurationSource.setAllowedOrigins(allowOrigin);
//        // Cho phép tất cả các phương thức HTTP (GET, POST, PUT, DELETE, v.v.)
//        configurationSource.setAllowedMethods(List.of("*"));
//        // Cho phép tất cả các headers
//        configurationSource.setAllowedHeaders(List.of("*"));
//        // Tạo một đối tượng UrlBasedCorsConfigurationSource để đăng ký cấu hình CORS
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        // Đăng ký cấu hình CORS cho tất cả các đường dẫn URL
//        source.registerCorsConfiguration("/**", configurationSource);
//        return source;
//    }
//}
