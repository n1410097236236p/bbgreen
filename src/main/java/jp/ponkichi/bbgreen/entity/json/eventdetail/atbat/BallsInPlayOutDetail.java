package jp.ponkichi.bbgreen.entity.json.eventdetail.atbat;

import jp.ponkichi.bbgreen.entity.constants.BattedBallType;
import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class BallsInPlayOutDetail extends AtBatInfo implements EventDetail {

  private BallsInPlayOutDetail(BattedBallType battedBallType, Position direction) {
    super(battedBallType, direction);
  }

  @Override
  public ActionType getEventType() {
    return ActionType.BALLS_IN_PLAY_OUT;
  }

  public static BallsInPlayOutDetail create(BattedBallType battedBallType, Position direction) {
    return new BallsInPlayOutDetail(battedBallType, direction);
  }
}
