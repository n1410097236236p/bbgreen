package jp.ponkichi.bbgreen.controller;

import jp.ponkichi.bbgreen.dto.LoginRequest;
import jp.ponkichi.bbgreen.dto.LoginResponse;
import jp.ponkichi.bbgreen.dto.UserRegistrationRequest;
import jp.ponkichi.bbgreen.dto.UserRegistrationResponse;
import jp.ponkichi.bbgreen.entity.User;
import jp.ponkichi.bbgreen.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @SecurityRequirement(name = "")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.authenticate(request.username(), request.password());
    }

    @PostMapping("/register")
    public UserRegistrationResponse register(@Valid @RequestBody UserRegistrationRequest request) {
        User user = authService.register(request);
        return new UserRegistrationResponse(user.getId(), user.getUsername());
    }
}
