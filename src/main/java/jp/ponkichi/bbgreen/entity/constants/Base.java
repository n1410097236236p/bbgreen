package jp.ponkichi.bbgreen.entity.constants;

import lombok.Getter;

@Getter
public enum Base {
  FIRST(1), SECOND(2), THIRD(3), HOME(4);

  private final int code;

  Base(int code) {
    this.code = code;
  }
}
