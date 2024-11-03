package com.company.media.controller;

import com.company.media.exceptions.ResourceNotFoundException;
import com.company.media.service.follower.IFollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class FollowerController {
    private final IFollowerService followerService;

    @PostMapping("/follow")
    public String addFollower(@RequestParam Long userId){
        try {
            followerService.addFollower(userId);
            return "redirect:/index";
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

}
