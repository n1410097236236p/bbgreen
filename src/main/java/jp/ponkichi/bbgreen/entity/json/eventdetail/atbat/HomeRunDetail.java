package jp.ponkichi.bbgreen.entity.json.eventdetail.atbat;

import jp.ponkichi.bbgreen.entity.constants.BattedBallType;
import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class HomeRunDetail extends AtBatInfo implements EventDetail {

  private HomeRunDetail(BattedBallType battedBallType, Position direction) {
    super(battedBallType, direction);
  }

  @Override
  public ActionType getEventType() {
    return ActionType.HOME_RUN;
  }

  public static HomeRunDetail of(BattedBallType battedBallType, Position direction) {
    return new HomeRunDetail(battedBallType, direction);
  }
}
