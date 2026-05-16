package jp.ponkichi.bbgreen.dto;

import jakarta.validation.constraints.NotBlank;

public record PlayerRequest(@NotBlank String name) {
}
