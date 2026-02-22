package jp.ponkichi.bbgreen.healthcheck.dto;

//public record Message(String text) { }

public class Message {
    private final String text;

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
