package com.company.media.service.follower;

public interface IFollowerService {
    void addFollower(Long userId);
    boolean checkFollowerIsExists(Long userId,Long followerId);
    void deleteFollower(Long userId,Long followerId);
}
