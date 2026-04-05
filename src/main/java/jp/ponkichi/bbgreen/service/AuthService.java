package jp.ponkichi.bbgreen.service;

import jp.ponkichi.bbgreen.security.JwtProvider;
import jp.ponkichi.bbgreen.dto.LoginResponse;
import jp.ponkichi.bbgreen.dto.UserRegistrationRequest;
import jp.ponkichi.bbgreen.dto.element.Password;
import jp.ponkichi.bbgreen.entity.User;
import jp.ponkichi.bbgreen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public LoginResponse authenticate(String username, Password.Raw rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid userName or password"));

        boolean passwordMatches = user.checkPassword(rawPassword, passwordEncoder);
        if (!passwordMatches) {
            throw new IllegalArgumentException("Invalid userName or password");
        }

        String token = jwtProvider.createToken(username);
        return new LoginResponse(token, user.getId(), user.getUsername());
    }

    public User register(UserRegistrationRequest request) {
        String username = request.username();
        Password.Raw rawPassword = request.password();
        String email = request.email();

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = User.of(username, rawPassword, passwordEncoder);
        user.setEmail(email);
        return userRepository.save(user);
    }
}
