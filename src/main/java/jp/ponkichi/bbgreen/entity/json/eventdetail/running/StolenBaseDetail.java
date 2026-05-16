package jp.ponkichi.bbgreen.entity.json.eventdetail.running;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.Base;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class StolenBaseDetail extends RunningInfo implements EventDetail {
  private StolenBaseDetail(Player runner, Base startBase, Base endBase) {
    super(runner, startBase, endBase);
  }

  @Override
  public EventType getEventType() {
    return EventType.STOLEN_BASE;
  }

  public static StolenBaseDetail of(Player runner, Base startBase, Base endBase) {
    return new StolenBaseDetail(runner, startBase, endBase);
  }
}
