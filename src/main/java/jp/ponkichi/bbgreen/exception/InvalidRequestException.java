package jp.ponkichi.bbgreen.exception;

public class InvalidRequestException extends CustomException {
  public InvalidRequestException(String message) {
    super(message, "INVALID_REQUEST");
  }
}
