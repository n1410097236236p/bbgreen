package jp.ponkichi.bbgreen.entity.json.eventdetail.pitch;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class SwingAndMissDetail extends PitchInfo implements EventDetail {
  private SwingAndMissDetail(Player pitcher, Player batter) {
    super(pitcher, batter);
  }

  @Override
  public ActionType getEventType() {
    return ActionType.SWING_AND_MISS;
  }

  public static SwingAndMissDetail of(Player pitcher, Player batter) {
    return new SwingAndMissDetail(pitcher, batter);
  }
}
