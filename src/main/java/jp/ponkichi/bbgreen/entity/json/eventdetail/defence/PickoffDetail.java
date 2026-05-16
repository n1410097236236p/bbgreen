package jp.ponkichi.bbgreen.entity.json.eventdetail.defence;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class PickoffDetail extends DefenceInfo implements EventDetail {

  private PickoffDetail(Player fielder, Position position) {
    super(fielder, position);
  }

  @Override
  public EventType getEventType() {
    return EventType.PICKOFF;
  }

  public static PickoffDetail of(Player fielder, Position position) {
    return new PickoffDetail(fielder, position);
  }

}
