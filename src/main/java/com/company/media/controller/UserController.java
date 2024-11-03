package com.company.media.controller;

import com.company.media.dto.JwtResponse;
import com.company.media.dto.UserLoginDto;
import com.company.media.dto.UserRegistrationDto;
import com.company.media.exceptions.AlreadyExistsException;
import com.company.media.service.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@Controller
public class UserController {
    private final IUserService userService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody UserRegistrationDto registration){
        try {
            userService.registerUser(registration);
            return "redirect:/login";
        } catch (AlreadyExistsException e) {
            throw new AlreadyExistsException(e.getMessage());
        }
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDto loginReq){
        try {
            JwtResponse response = userService.loginUser(loginReq);
            return ResponseEntity.ok(response.getToken());
        } catch (Exception e){
           return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
