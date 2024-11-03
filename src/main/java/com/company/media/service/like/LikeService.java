package com.company.media.service.like;

import com.company.media.model.MediaPost;
import com.company.media.model.User;
import com.company.media.repository.UserRepository;
import com.company.media.security.user.UserDetail;
import com.company.media.service.mediaPost.IMediaPostService;
import com.company.media.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService implements ILikeService{
    private final UserRepository userRepository;
    private final IUserService userService;
    private final IMediaPostService postService;

    @Override
    public void addLike(Long postId) {
       var auth = SecurityContextHolder.getContext().getAuthentication();
       UserDetail authenticatedUser = (UserDetail) auth.getPrincipal();

       User user = userService.findById(authenticatedUser.getId());
       MediaPost post = postService.findById(postId);

       if(!user.getLikedPosts().contains(post)){
           user.getLikedPosts().add(post);
           post.getLikedByUsers().add(user);
       }else{
          removeLike(user,post);
       }

       userRepository.save(user);
    }


    private void removeLike(User user, MediaPost post) {
        user.getLikedPosts().remove(post);
        post.getLikedByUsers().remove(user);
    }


}
