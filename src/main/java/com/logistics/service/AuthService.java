// File: src/main/java/com/logistics/service/AuthService.java
package com.logistics.service;

import com.logistics.dto.JwtResponse;
import com.logistics.dto.LoginRequest;
import com.logistics.dto.RegisterRequest;
import com.logistics.model.User;
import com.logistics.repository.UserRepository;
import com.logistics.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        
        return new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }
    
    @Transactional
    public void register(RegisterRequest registerRequest) {
        // Check if username or email exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }
        
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }
        
        // Create new user's account
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        
        Set<String> strRoles = registerRequest.getRoles();
        Set<String> roles = new HashSet<>();
        
        if (strRoles == null || strRoles.isEmpty()) {
            roles.add("ROLE_USER");
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> roles.add("ROLE_ADMIN");
                    case "mod" -> roles.add("ROLE_MODERATOR");
                    default -> roles.add("ROLE_USER");
                }
            });
        }
        
        user.setRoles(roles);
        userRepository.save(user);
    }
}