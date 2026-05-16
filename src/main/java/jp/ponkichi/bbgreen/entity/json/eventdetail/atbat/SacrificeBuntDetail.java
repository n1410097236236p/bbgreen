package jp.ponkichi.bbgreen.entity.json.eventdetail.atbat;

import jp.ponkichi.bbgreen.entity.constants.BattedBallType;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class SacrificeBuntDetail extends AtBatInfo implements EventDetail {

  private SacrificeBuntDetail(BattedBallType battedBallType, Position direction) {
    super(battedBallType, direction);
  }

  @Override
  public EventType getEventType() {
    return EventType.SACRIFICE_BUNT;
  }

  public static SacrificeBuntDetail of(BattedBallType battedBallType, Position direction) {
    return new SacrificeBuntDetail(battedBallType, direction);
  }
}
