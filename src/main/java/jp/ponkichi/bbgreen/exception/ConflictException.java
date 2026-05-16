package jp.ponkichi.bbgreen.exception;

public class ConflictException extends CustomException {
  public ConflictException(String message) {
    super(message, "CONFLICT");
  }
}
