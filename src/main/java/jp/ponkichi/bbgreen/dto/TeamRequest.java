package jp.ponkichi.bbgreen.dto;

import jakarta.validation.constraints.NotBlank;

public record TeamRequest(@NotBlank String name) {
}
