package jp.ponkichi.bbgreen.entity.json.eventdetail.pitch;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class BuntFoulBallDetail extends PitchInfo implements EventDetail {
  private BuntFoulBallDetail(Player pitcher, Player batter) {
    super(pitcher, batter);
  }

  @Override
  public ActionType getEventType() {
    return ActionType.BUNT_FOUL_BALL;
  }

  public static BuntFoulBallDetail of(Player pitcher, Player batter) {
    return new BuntFoulBallDetail(pitcher, batter);
  }
}
