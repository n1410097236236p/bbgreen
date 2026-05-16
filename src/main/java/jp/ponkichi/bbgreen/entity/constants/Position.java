package jp.ponkichi.bbgreen.entity.constants;

import lombok.Getter;

@Getter
public enum Position {
  PITCHER(1), //
  CATCHER(2), //
  FIRST_BASE(3), //
  SECOND_BASE(4), //
  THIRD_BASE(5), //
  SHORT_STOP(6), //
  LEFT_FIELD(7), //
  CENTER_FIELD(8), //
  RIGHT_FIELD(9), //
  DESIGNATED_HITTER(10), //
  PINCH_HITTER(11); //

  private final int code;

  Position(int code) {
    this.code = code;
  }
}
