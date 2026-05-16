package jp.ponkichi.bbgreen.dto;

import jakarta.validation.constraints.NotBlank;

public record LeagueRequest(@NotBlank String name) {
}
