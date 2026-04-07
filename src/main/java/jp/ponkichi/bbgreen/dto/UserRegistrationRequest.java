package jp.ponkichi.bbgreen.dto;

import jakarta.validation.constraints.NotBlank;
import jp.ponkichi.bbgreen.dto.element.Password;

public record UserRegistrationRequest(@NotBlank String username, Password.Raw password,
    String email) {
}
