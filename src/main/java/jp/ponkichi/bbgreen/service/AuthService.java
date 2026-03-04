package jp.ponkichi.bbgreen.service;

import jp.ponkichi.bbgreen.security.JwtProvider;
import jp.ponkichi.bbgreen.dto.LoginResponse;
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

    public LoginResponse authenticate(String username, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid userName or password"));

        if (rawPassword == null || user.getPassword() == null ||
                !passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid userName or password");
        }

        String token = jwtProvider.createToken(username);
        return new LoginResponse(token, user.getId(), user.getUsername());
    }
}
