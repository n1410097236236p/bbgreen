package jp.ponkichi.bbgreen.entity.json.eventdetail.running;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.Base;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class CaughtStealingDetail extends RunningInfo implements EventDetail {
  private CaughtStealingDetail(Player runner, Base startBase, Base endBase) {
    super(runner, startBase, endBase);
  }

  @Override
  public EventType getEventType() {
    return EventType.CAUGHT_STEALING;
  }

  public static CaughtStealingDetail of(Player runner, Base startBase, Base endBase) {
    return new CaughtStealingDetail(runner, startBase, endBase);
  }
}
