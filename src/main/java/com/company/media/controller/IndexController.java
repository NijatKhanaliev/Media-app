package com.company.media.controller;

import com.company.media.dto.MediaPostWithFollowAndLikeStatus;
import com.company.media.model.MediaPost;
import com.company.media.repository.UserRepository;
import com.company.media.security.user.UserDetail;
import com.company.media.service.follower.IFollowerService;
import com.company.media.service.mediaPost.IMediaPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final IMediaPostService postService;
    private final IFollowerService followerService;
    private final UserRepository userRepository;

    @GetMapping("/index")
    public String index(Model model) {
        List<MediaPost> mediaPostList = postService.getAllPost();
        List<MediaPostWithFollowAndLikeStatus> mediaPostWithFollowAndLikeStatusList = new ArrayList<>();
        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetail authenticatedUser = (UserDetail) auth.getPrincipal();

            for (MediaPost m : mediaPostList) {
                boolean isFollow = followerService.checkFollowerIsExists(m.getUser().getId(), authenticatedUser.getId());
                boolean isLiked = userRepository.existsByIdAndLikedPostsId(authenticatedUser.getId(), m.getId());
                mediaPostWithFollowAndLikeStatusList.add(new MediaPostWithFollowAndLikeStatus(m, isFollow, isLiked));
            }

            model.addAttribute("mediaPostWithFollowAndLikeStatusList", mediaPostWithFollowAndLikeStatusList);

            return "index";
    }
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @PostMapping("/search")
    public String searchByName(Model model, @RequestParam(required = false) String name) {
        List<MediaPost> mediaPostList = postService.searchByName(name);
        model.addAttribute("mediaPostList", mediaPostList);

        return "index";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

}
