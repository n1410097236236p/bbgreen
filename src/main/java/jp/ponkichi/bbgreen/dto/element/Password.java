package jp.ponkichi.bbgreen.dto.element;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

public final class Password {
    private Password() {
        // Prevent instantiation
    }

    public static record Raw(char[] value) {
        public Raw {
            if (value == null || value.length == 0) {
                throw new IllegalArgumentException("Password is required");
            }
            value = Arrays.copyOf(value, value.length); // defensive copy
        }

        public String encode(PasswordEncoder encoder) {
            String passwordString = new String(this.value);
            try {
                return encoder.encode(passwordString);
            } finally {
                Arrays.fill(this.value, ' '); // Zero out the char array after use
                Arrays.fill(passwordString.toCharArray(), ' '); // Zero out the string char array
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
            if (rawPassword == null || rawPassword.value() == null || this.value == null) {
                return false;
            }
            String rawPasswordString = new String(rawPassword.value());
            try {
                return encoder.matches(rawPasswordString, this.value);
            } finally {
                Arrays.fill(rawPassword.value(), ' '); // Zero out the char array
                Arrays.fill(rawPasswordString.toCharArray(), ' '); // Zero out the string char array
            }
        }

        @Override
        public String toString() {
            return "EncodedPassword [value=PROTECTED]";
        }
    }
}
