package jp.ponkichi.bbgreen.dto.element;

import lombok.Getter;

public class Message {
    @Getter
    private final String text;

    public Message(String text) {
        this.text = text;
    }
}

//public record Message(String text) { }

