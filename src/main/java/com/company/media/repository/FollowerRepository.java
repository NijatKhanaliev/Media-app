package com.company.media.repository;

import com.company.media.model.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    boolean existsByUserIdAndFollowerId(Long userId,Long followerId);

    void deleteByUserIdAndFollowerId(Long userId,Long followerId);
}