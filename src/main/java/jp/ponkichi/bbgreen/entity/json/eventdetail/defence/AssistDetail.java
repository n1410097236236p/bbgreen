package jp.ponkichi.bbgreen.entity.json.eventdetail.defence;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class AssistDetail extends DefenceInfo implements EventDetail {

  private AssistDetail(Player fielder, Position position) {
    super(fielder, position);
  }

  @Override
  public EventType getEventType() {
    return EventType.ASSIST;
  }

  public static AssistDetail of(Player fielder, Position position) {
    return new AssistDetail(fielder, position);
  }

}
