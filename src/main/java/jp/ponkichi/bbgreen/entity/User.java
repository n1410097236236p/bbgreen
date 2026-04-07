package jp.ponkichi.bbgreen.entity;

import java.time.LocalDateTime;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jp.ponkichi.bbgreen.dto.element.Password;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  @Getter
  @Setter
  private String username;

  @Column(nullable = false)
  @Setter
  private Password.Encoded password;

  @Column(unique = true, length = 100)
  @Getter
  @Setter
  private String email;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  public static User of(@NonNull String username, @NonNull Password.Encoded encodedPassword) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(encodedPassword);
    return user;
  }

  public boolean checkPassword(Password.Raw rawPassword, PasswordEncoder passwordEncoder) {
    return this.password.matches(rawPassword, passwordEncoder);
  }
}
