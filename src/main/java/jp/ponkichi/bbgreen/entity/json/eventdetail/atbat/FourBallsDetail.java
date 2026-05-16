package jp.ponkichi.bbgreen.entity.json.eventdetail.atbat;

import jp.ponkichi.bbgreen.entity.constants.BattedBallType;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class FourBallsDetail extends AtBatInfo implements EventDetail {

  private FourBallsDetail(BattedBallType battedBallType, Position direction) {
    super(battedBallType, direction);
  }

  @Override
  public EventType getEventType() {
    return EventType.FOUR_BALLS;
  }

  @Override
  public BattedBallType getBattedBallType() {
    return null;
  }

  @Override
  public Position getDirection() {
    return null;
  }

  public static FourBallsDetail create() {
    return new FourBallsDetail(null, null);
  }
}
