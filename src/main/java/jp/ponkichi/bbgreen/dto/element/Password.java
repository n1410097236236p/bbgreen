package jp.ponkichi.bbgreen.dto.element;

import org.springframework.security.crypto.password.PasswordEncoder;

public final class Password {
  private Password() {
    // Prevent instantiation
  }

  public static record Raw(char[] value) {
    public Raw {
      if (value == null || value.length == 0) {
        throw new IllegalArgumentException("Password is required");
      }
    }

    public String encode(PasswordEncoder encoder) {
      try {
        return encoder.encode(java.nio.CharBuffer.wrap(this.value));
      } finally {
        java.util.Arrays.fill(this.value, ' ');
      }
    }

    @Override
    public String toString() {
      return "RawPassword [value=PROTECTED]";
    }
  }

  public static record Encoded(String value) {
    public Encoded {
      if (value == null || value.isBlank()) {
        throw new IllegalArgumentException("Encoded password is required");
      }
    }

    public boolean matches(Raw rawPassword, PasswordEncoder encoder) {
      if (rawPassword == null || rawPassword.value() == null) {
        return false;
      }
      CharSequence charSequence = java.nio.CharBuffer.wrap(rawPassword.value());
      return encoder.matches(charSequence, this.value);
    }

    @Override
    public String toString() {
      return "EncodedPassword [value=PROTECTED]";
    }
  }
}
