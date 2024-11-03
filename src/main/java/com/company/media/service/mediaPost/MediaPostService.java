package com.company.media.service.mediaPost;

import com.company.media.exceptions.ResourceNotFoundException;
import com.company.media.model.MediaPost;
import com.company.media.model.User;
import com.company.media.repository.MediaPostRepository;
import com.company.media.security.user.UserDetail;
import com.company.media.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MediaPostService implements IMediaPostService{
    private final MediaPostRepository postRepository;
    private final IUserService userService;

    @Override
    public List<MediaPost> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public MediaPost findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post not found!"));
    }

    @Override
    public List<MediaPost> searchByName(String name) {
        return postRepository.searchByName(name);
    }

    @Override
    public MediaPost createPost(String name, String description) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetail authenticatedUser = (UserDetail) auth.getPrincipal();

        User user = userService.findById(authenticatedUser.getId());

        MediaPost newPost = new MediaPost(name,description, LocalDateTime.now(),user);

        return postRepository.save(newPost);
    }

}
