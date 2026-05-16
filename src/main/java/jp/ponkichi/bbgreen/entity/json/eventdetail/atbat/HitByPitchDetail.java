package jp.ponkichi.bbgreen.entity.json.eventdetail.atbat;

import jp.ponkichi.bbgreen.entity.constants.BattedBallType;
import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class HitByPitchDetail extends AtBatInfo implements EventDetail {

  public HitByPitchDetail(BattedBallType battedBallType, Position direction) {
    super(battedBallType, direction);
  }

  @Override
  public ActionType getEventType() {
    return ActionType.HIT_BY_PITCH;
  }

  @Override
  public BattedBallType getBattedBallType() {
    return null;
  }

  @Override
  public Position getDirection() {
    return null;
  }

  public static HitByPitchDetail create() {
    return new HitByPitchDetail(null, null);
  }
}
