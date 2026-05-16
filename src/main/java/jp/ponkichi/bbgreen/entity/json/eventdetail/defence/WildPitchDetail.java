package jp.ponkichi.bbgreen.entity.json.eventdetail.defence;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class WildPitchDetail extends DefenceInfo implements EventDetail {

  private WildPitchDetail(Player fielder, Position position) {
    super(fielder, position);
  }

  @Override
  public EventType getEventType() {
    return EventType.WILD_PITCH;
  }

  public static WildPitchDetail of(Player fielder, Position position) {
    return new WildPitchDetail(fielder, position);
  }
}
