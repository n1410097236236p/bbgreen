package jp.ponkichi.bbgreen.healthcheck.dto;

import lombok.Getter;

public class Message {
    @Getter
    private final String text;

    public Message(String text) {
        this.text = text;
    }
}

//public record Message(String text) { }

