package com.secure.secureservice.auth.service.impl;

import com.secure.secureservice.auth.service.AuthenticationService;
import com.secure.secureservice.config.JwtService;
import com.secure.secureservice.domain.AuthenticationRequest;
import com.secure.secureservice.domain.AuthenticationResponse;
import com.secure.secureservice.domain.RegisterRequest;
import com.secure.secureservice.domain.User;
import com.secure.secureservice.domain.enums.Role;
import com.secure.secureservice.exceptions.UserNotFoundException;
import com.secure.secureservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = buildUser(registerRequest);
        userRepository.save(user);
        return buildAuthResponse(user);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationProcess(authenticationRequest);
        var user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return buildAuthResponse(user);
    }

    private void authenticationProcess(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
    }

    private AuthenticationResponse buildAuthResponse(User user) {
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private User buildUser(RegisterRequest registerRequest) {
        return User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
    }
}
