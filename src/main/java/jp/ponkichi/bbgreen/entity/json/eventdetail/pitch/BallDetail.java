package jp.ponkichi.bbgreen.entity.json.eventdetail.pitch;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class BallDetail extends PitchInfo implements EventDetail {
  private BallDetail(Player pitcher, Player batter) {
    super(pitcher, batter);
  }

  @Override
  public ActionType getEventType() {
    return ActionType.BALL;
  }

  public static BallDetail of(Player pitcher, Player batter) {
    return new BallDetail(pitcher, batter);
  }
}
