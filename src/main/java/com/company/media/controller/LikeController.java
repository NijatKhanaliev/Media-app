package com.company.media.controller;

import com.company.media.exceptions.ResourceNotFoundException;
import com.company.media.service.like.ILikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class LikeController {
    private final ILikeService likeService;

    @PostMapping("/like")
    public String likeVideo(@RequestParam Long postId){
        try {
            likeService.addLike(postId);
            return "redirect:/index";
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

}
