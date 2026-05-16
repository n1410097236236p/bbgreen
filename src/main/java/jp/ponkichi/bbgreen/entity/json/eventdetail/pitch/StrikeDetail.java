package jp.ponkichi.bbgreen.entity.json.eventdetail.pitch;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class StrikeDetail extends PitchInfo implements EventDetail {
  private StrikeDetail(Player pitcher, Player batter) {
    super(pitcher, batter);
  }

  @Override
  public EventType getEventType() {
    return EventType.STRIKE;
  }

  public static StrikeDetail of(Player pitcher, Player batter) {
    return new StrikeDetail(pitcher, batter);
  }
}
