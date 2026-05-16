package jp.ponkichi.bbgreen.entity.json.eventdetail.atbat;

import jp.ponkichi.bbgreen.entity.constants.BattedBallType;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class UncaughtThirdStrikeDetail extends AtBatInfo implements EventDetail {

  private UncaughtThirdStrikeDetail(BattedBallType battedBallType, Position direction) {
    super(battedBallType, direction);
  }

  @Override
  public EventType getEventType() {
    return EventType.UNCAUGHT_THIRD_STRIKE;
  }

  @Override
  public BattedBallType getBattedBallType() {
    return null;
  }

  @Override
  public Position getDirection() {
    return null;
  }

  public static UncaughtThirdStrikeDetail create() {
    return new UncaughtThirdStrikeDetail(null, null);
  }
}
