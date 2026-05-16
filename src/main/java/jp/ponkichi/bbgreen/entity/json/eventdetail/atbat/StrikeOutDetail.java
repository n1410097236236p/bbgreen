package jp.ponkichi.bbgreen.entity.json.eventdetail.atbat;

import jp.ponkichi.bbgreen.entity.constants.BattedBallType;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class StrikeOutDetail extends AtBatInfo implements EventDetail {

  private StrikeOutDetail(BattedBallType battedBallType, Position direction) {
    super(battedBallType, direction);
  }

  @Override
  public EventType getEventType() {
    return EventType.STRIKE_OUT;
  }

  @Override
  public BattedBallType getBattedBallType() {
    return null;
  }

  @Override
  public Position getDirection() {
    return null;
  }

  public static StrikeOutDetail create() {
    return new StrikeOutDetail(null, null);
  }
}
