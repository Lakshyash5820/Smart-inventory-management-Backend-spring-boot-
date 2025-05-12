package com.smartstock.service;

import com.smartstock.dto.AuthResponse;
import com.smartstock.dto.LoginRequest;
import com.smartstock.dto.RegisterRequest;
import com.smartstock.exception.CustomException;
import com.smartstock.model.User;
import com.smartstock.repository.UserRepository;
import com.smartstock.security.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new CustomException("Email is already taken!");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setName(registerRequest.getName());
        user.setBusinessType(registerRequest.getBusinessType());
        user.setRole("USER");

        userRepository.save(user);

        String token = jwtTokenUtil.generateToken(user);
        return new AuthResponse(user.getId(), user.getEmail(), user.getName(), 
                user.getBusinessType(), user.getRole(), token);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException("User not found"));

        String token = jwtTokenUtil.generateToken(user);
        return new AuthResponse(user.getId(), user.getEmail(), user.getName(), 
                user.getBusinessType(), user.getRole(), token);
    }

    public AuthResponse getCurrentUser() {
        User currentUser = userService.getCurrentUser();
        return new AuthResponse(currentUser.getId(), currentUser.getEmail(), 
                currentUser.getName(), currentUser.getBusinessType(), 
                currentUser.getRole(), null);
    }
}