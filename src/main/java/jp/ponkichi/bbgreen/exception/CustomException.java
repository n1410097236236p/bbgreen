package jp.ponkichi.bbgreen.exception;

import lombok.Getter;

public class CustomException extends RuntimeException {
  @Getter
  private final String errorCode;

  public CustomException(String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
  }
}
