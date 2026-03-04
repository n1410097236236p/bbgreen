package jp.ponkichi.bbgreen.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password; //hashed

    @Column(unique = true, length = 100)
    private String email;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    private User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public static User of(String username, String encodedPassword, String email) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("userName is required");
        }
        if (encodedPassword == null || encodedPassword.isBlank()) {
            throw new IllegalArgumentException("password is required");
        }
        return new User(username, encodedPassword, email);
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
