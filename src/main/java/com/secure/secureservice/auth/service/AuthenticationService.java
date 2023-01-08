package com.secure.secureservice.auth.service;

import com.secure.secureservice.domain.AuthenticationRequest;
import com.secure.secureservice.domain.AuthenticationResponse;
import com.secure.secureservice.domain.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
