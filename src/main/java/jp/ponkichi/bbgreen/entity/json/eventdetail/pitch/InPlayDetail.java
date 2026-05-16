package jp.ponkichi.bbgreen.entity.json.eventdetail.pitch;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class InPlayDetail extends PitchInfo implements EventDetail {
  private InPlayDetail(Player pitcher, Player batter) {
    super(pitcher, batter);
  }

  @Override
  public EventType getEventType() {
    return EventType.IN_PLAY;
  }

  public static InPlayDetail of(Player pitcher, Player batter) {
    return new InPlayDetail(pitcher, batter);
  }
}
