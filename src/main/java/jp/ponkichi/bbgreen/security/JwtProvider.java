package jp.ponkichi.bbgreen.security;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
  private final SecretKey key;
  private final long expirationTime;

  public JwtProvider(@Value("${jwt.secret}") String secret,
      @Value("${jwt.expiration}") Long expirationTime) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
    this.expirationTime = expirationTime;
  }

  public String createToken(String username) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expirationTime);

    return Jwts.builder().subject(username).issuedAt(now).expiration(expiryDate).signWith(key)
        .compact();
  }

  public String getUsernameFromToken(String token) {
    try {
      return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload()
          .getSubject();
    } catch (Exception e) {
      // トークンが改ざんされている、または期限切れの場合
      return null;
    }
  }
}
