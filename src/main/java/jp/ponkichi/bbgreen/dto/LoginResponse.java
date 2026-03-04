package jp.ponkichi.bbgreen.dto;

public record LoginResponse(String token, Long userId, String username) {
}
