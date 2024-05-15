package com.khaphp.userservice.repository;

import com.khaphp.userservice.entity.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSystemRepository extends JpaRepository<UserSystem, String> {
    UserSystem findByEmail(String email);

    Optional<UserSystem> findByUsername(String username);
    UserSystem findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


    @Query(value = "SELECT * FROM `user-service`.user_system where birthday like ?1 and role = ?2", nativeQuery = true)
    List<UserSystem> findByBirthdayByRole(String dateNoew, String role);
}
