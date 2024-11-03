package com.company.media.service.follower;

import com.company.media.model.Follower;
import com.company.media.model.User;
import com.company.media.repository.FollowerRepository;
import com.company.media.security.user.UserDetail;
import com.company.media.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowerService implements IFollowerService{
    private final FollowerRepository followerRepository;
    private final IUserService userService;

    @Transactional
    @Override
    public void addFollower(Long userId) {
        User user = userService.findById(userId);
        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetail authenticatedUser = (UserDetail) auth.getPrincipal();

        if(checkFollowerIsExists(userId,authenticatedUser.getId())){
            deleteFollower(userId,authenticatedUser.getId()); // if follower already follow user delete it
        }else{
            Follower follower = new Follower(authenticatedUser.getId(),user);

            followerRepository.save(follower); // follower added
        }

    }

    @Override
    public boolean checkFollowerIsExists(Long userId,Long followerId) {
        return followerRepository.existsByUserIdAndFollowerId(userId,followerId);
    }

    @Override
    public void deleteFollower(Long userId, Long followerId) {
        followerRepository.deleteByUserIdAndFollowerId(userId,followerId);
    }
}
