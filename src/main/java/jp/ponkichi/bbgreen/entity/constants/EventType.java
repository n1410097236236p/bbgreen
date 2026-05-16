package jp.ponkichi.bbgreen.entity.constants;

import lombok.Getter;

@Getter
public enum EventType {
  // --- 投球結果系 ---
  STRIKE(PrimitiveType.PITCH), //
  SWING_AND_MISS(PrimitiveType.PITCH), //
  BALL(PrimitiveType.PITCH), //
  FOUL_BALL(PrimitiveType.PITCH), //
  BUNT_FOUL_BALL(PrimitiveType.PITCH), //
  IN_PLAY(PrimitiveType.PITCH), //

  // --- 打撃結果系 ---
  SINGLE(PrimitiveType.AT_BAT_HIT), //
  DOUBLE(PrimitiveType.AT_BAT_HIT), //
  TRIPLE(PrimitiveType.AT_BAT_HIT), //
  HOME_RUN(PrimitiveType.AT_BAT_HIT), //
  FOUR_BALLS(PrimitiveType.AT_BAT_ON_BASE), //
  HIT_BY_PITCH(PrimitiveType.AT_BAT_ON_BASE), //
  BALLS_IN_PLAY_OUT(PrimitiveType.AT_BAT_OUT), //
  STRIKE_OUT(PrimitiveType.AT_BAT_OUT), // スリーバント失敗を含む
  REACH_ON_ERROR(PrimitiveType.AT_BAT_OUT), //
  UNCAUGHT_THIRD_STRIKE(PrimitiveType.AT_BAT_OUT), //
  SACRIFICE_BUNT(PrimitiveType.AT_BAT_SACRIFICE), //
  SACRIFICE_FLY(PrimitiveType.AT_BAT_SACRIFICE), //

  // --- 守備系 ---
  ASSIST(PrimitiveType.DEFENCE), //
  PUT_OUT(PrimitiveType.DEFENCE), //
  PICKOFF(PrimitiveType.DEFENCE), //
  ERROR(PrimitiveType.DEFENCE), //
  PASSED_BALL(PrimitiveType.DEFENCE), //
  WILD_PITCH(PrimitiveType.DEFENCE), //

  // --- 走塁系 ---
  STOLEN_BASE(PrimitiveType.RUNNING), //
  CAUGHT_STEALING(PrimitiveType.RUNNING), //
  PICKED_OFF(PrimitiveType.RUNNING), //
  TAG_UP(PrimitiveType.RUNNING), //

  // --- その他 ---
  PLAY_BALL(PrimitiveType.OTHER), //
  TURN_OVER(PrimitiveType.OTHER), //
  GAME_END(PrimitiveType.OTHER), //
  CHANGE_PLAYER(PrimitiveType.OTHER), //
  CHANGE_POSITION(PrimitiveType.OTHER); //

  private final PrimitiveType primitiveType;

  EventType(PrimitiveType primitiveType) {
    this.primitiveType = primitiveType;
  }
}
