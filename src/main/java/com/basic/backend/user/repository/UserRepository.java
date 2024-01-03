package com.basic.backend.user.repository;

import com.basic.backend.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    Page<User> findAllByUserIdContainingOrNameContaining(String userId, String Name, Pageable pageable);
}
