package com.company.media.service.user;

import com.company.media.dto.JwtResponse;
import com.company.media.dto.UserLoginDto;
import com.company.media.dto.UserRegistrationDto;
import com.company.media.model.User;

public interface IUserService {
    User findByFirstName(String name);
    User findById(Long userId);
    void registerUser(UserRegistrationDto userRequest);
    JwtResponse loginUser(UserLoginDto loginDto);
    User getAuthenticatedUser();
    User findByEmail(String email);
    void sendPasswordResetEmail(String email);
    void updatePassword(String email,String password);
}
