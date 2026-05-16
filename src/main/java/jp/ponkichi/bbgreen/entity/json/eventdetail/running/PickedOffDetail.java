package jp.ponkichi.bbgreen.entity.json.eventdetail.running;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.Base;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class PickedOffDetail extends RunningInfo implements EventDetail {
  private PickedOffDetail(Player runner, Base startBase, Base endBase) {
    super(runner, startBase, endBase);
  }

  @Override
  public EventType getEventType() {
    return EventType.PICKED_OFF;
  }

  public static PickedOffDetail of(Player runner, Base startBase) {
    return new PickedOffDetail(runner, startBase, null);
  }
}
