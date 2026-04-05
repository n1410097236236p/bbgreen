package jp.ponkichi.bbgreen.dto;

import jp.ponkichi.bbgreen.dto.element.Password;

public record LoginRequest (String username, Password.Raw password) {
}
