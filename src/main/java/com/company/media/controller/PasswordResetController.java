package com.company.media.controller;

import com.company.media.exceptions.UserNotFoundException;
import com.company.media.security.jwt.JwtUtils;
import com.company.media.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class PasswordResetController {
    private final IUserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/request-password-reset")
    public String resetPassword(@RequestParam String email){
        try {
            userService.sendPasswordResetEmail(email);
            return "error";
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String token, Model model){
        if(jwtUtils.isTokenValid(token)){
            String email = jwtUtils.getUsernameFromToken(token);
            model.addAttribute("email",email);
            return "resetPassword";
        }else{
            return "error";
        }
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam String email,@RequestParam String newPassword){
        try {
            userService.updatePassword(email,newPassword);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }

        return "redirect:/error";
    }

}
