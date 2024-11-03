package com.company.media.repository;

import com.company.media.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirstName(String name);
    boolean existsByIdAndLikedPostsId(Long userId,Long postId);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}