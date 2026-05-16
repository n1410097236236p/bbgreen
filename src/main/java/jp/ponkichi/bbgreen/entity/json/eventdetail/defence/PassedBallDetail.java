package jp.ponkichi.bbgreen.entity.json.eventdetail.defence;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class PassedBallDetail extends DefenceInfo implements EventDetail {

  private PassedBallDetail(Player fielder, Position position) {
    super(fielder, position);
  }

  @Override
  public ActionType getEventType() {
    return ActionType.PASSED_BALL;
  }

  public static PassedBallDetail of(Player fielder, Position position) {
    return new PassedBallDetail(fielder, position);
  }

}
