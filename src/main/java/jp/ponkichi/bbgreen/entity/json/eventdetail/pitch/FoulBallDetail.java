package jp.ponkichi.bbgreen.entity.json.eventdetail.pitch;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class FoulBallDetail extends PitchInfo implements EventDetail {
  private FoulBallDetail(Player pitcher, Player batter) {
    super(pitcher, batter);
  }

  @Override
  public ActionType getEventType() {
    return ActionType.FOUL_BALL;
  }

  public static FoulBallDetail of(Player pitcher, Player batter) {
    return new FoulBallDetail(pitcher, batter);
  }
}
