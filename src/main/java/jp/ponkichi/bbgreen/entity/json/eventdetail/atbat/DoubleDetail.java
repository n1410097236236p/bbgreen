package jp.ponkichi.bbgreen.entity.json.eventdetail.atbat;

import jp.ponkichi.bbgreen.entity.constants.BattedBallType;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class DoubleDetail extends AtBatInfo implements EventDetail {

  private DoubleDetail(BattedBallType battedBallType, Position direction) {
    super(battedBallType, direction);
  }

  @Override
  public EventType getEventType() {
    return EventType.DOUBLE;
  }

  public static DoubleDetail of(BattedBallType battedBallType, Position direction) {
    return new DoubleDetail(battedBallType, direction);
  }
}
