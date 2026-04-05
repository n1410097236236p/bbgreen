package jp.ponkichi.bbgreen.dto;

import jp.ponkichi.bbgreen.dto.element.Password;
import jakarta.validation.constraints.NotBlank;

public record UserRegistrationRequest(@NotBlank String username, Password.Raw password, String email) {
}
