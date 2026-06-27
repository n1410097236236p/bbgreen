package jp.ponkichi.bbgreen.dto.element;

import lombok.Getter;

public class Message {
  @Getter
  private final String sayatext;

  public Message(String text) {
    this.sayatext = text;
  }
}

// public record Message(String text) { }

