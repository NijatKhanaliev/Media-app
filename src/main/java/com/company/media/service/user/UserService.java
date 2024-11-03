package com.company.media.service.user;

import com.company.media.dto.JwtResponse;
import com.company.media.dto.UserLoginDto;
import com.company.media.dto.UserRegistrationDto;
import com.company.media.exceptions.AlreadyExistsException;
import com.company.media.exceptions.ResourceNotFoundException;
import com.company.media.exceptions.UserNotFoundException;
import com.company.media.model.Role;
import com.company.media.model.User;
import com.company.media.repository.RoleRepository;
import com.company.media.repository.UserRepository;
import com.company.media.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final @Lazy JwtUtils jwtUtils;
    private final RoleRepository roleRepository;
    private final JavaMailSender mailSender;

    @Override
    public User findByFirstName(String name) {
        return userRepository.findByFirstName(name)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    @Override
    public void registerUser(UserRegistrationDto userRequest) {
        Optional.of(userRequest)
                .filter(user -> !userRepository.existsByEmail(userRequest.getEmail()))
                .map(req -> {
                    Role userRole = roleRepository.findByName("USER_ROLE").get();

                    User user = new User();
                    user.setEmail(userRequest.getEmail());
                    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
                    user.setRoles(Set.of(userRole));

                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("This email is already signed up."));
    }

    @Override
    public JwtResponse loginUser(UserLoginDto loginReq) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtUtils.generateTokenForUser(auth);

        return new JwtResponse(token);
    }

    @Override
    public User getAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User is not exists."));
    }

    @Override
    public void sendPasswordResetEmail(String email) {
        User user = findByEmail(email);

        String resetToken = jwtUtils.generatePasswordResetToken(user.getEmail(),user.getRoles(),user.getId());
        String resetLink = "http://localhost:8080/reset-password?token=" + resetToken;

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Reset your media app password");
        mailMessage.setText("Hi " + user.getFirstName() + ",\n\nClick the link to reset password: " + resetLink);

        mailSender.send(mailMessage);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        User user = findByEmail(email);

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }
}

