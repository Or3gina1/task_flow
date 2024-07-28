package com.primary.taskFlow.controller.auth;

import com.primary.taskFlow.dto.AuthenticationRequest;
import com.primary.taskFlow.dto.AuthenticationResponse;
import com.primary.taskFlow.dto.SignupRequest;
import com.primary.taskFlow.dto.UserDto;
import com.primary.taskFlow.entities.User;
import com.primary.taskFlow.repositories.UserRepository;
import com.primary.taskFlow.services.jwt.UserService;
import com.primary.taskFlow.services.auth.AuthService;
import com.primary.taskFlow.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Пользователь с таким E-mail, уже существует");
        UserDto createUserDto = authService.signupUser(signupRequest);
        if (createUserDto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь не создан");
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserDto);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неправильное имя пользователя или пароль");
        }

        final UserDetails userDetails = userService.userDetailService().loadUserByUsername(
                authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;
    }
}