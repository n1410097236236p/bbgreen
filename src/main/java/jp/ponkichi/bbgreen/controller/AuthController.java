package jp.ponkichi.bbgreen.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jp.ponkichi.bbgreen.dto.LoginRequest;
import jp.ponkichi.bbgreen.dto.LoginResponse;
import jp.ponkichi.bbgreen.dto.UserRegistrationRequest;
import jp.ponkichi.bbgreen.dto.UserRegistrationResponse;
import jp.ponkichi.bbgreen.dto.element.Password;
import jp.ponkichi.bbgreen.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  @SecurityRequirement(name = "")
  public LoginResponse login(@RequestBody LoginRequest request) {
    Password.Raw rawPassword = request.password();
    try {
      return authService.authenticate(request.username(), rawPassword);
    } finally {
      java.util.Arrays.fill(rawPassword.value(), '0');
    }
  }

  @PostMapping("/register")
  public UserRegistrationResponse register(@Valid @RequestBody UserRegistrationRequest request) {
    Password.Raw rawPassword = request.password();
    try {
      Password.Encoded encodedPassword = new Password.Encoded(rawPassword.encode(passwordEncoder));
      return authService.register(request.username(), encodedPassword, request.email());
    } finally {
      java.util.Arrays.fill(rawPassword.value(), '0');
    }
  }

  // ToDo: Change Password
}
