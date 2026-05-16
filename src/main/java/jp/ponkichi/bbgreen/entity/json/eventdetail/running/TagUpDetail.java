package jp.ponkichi.bbgreen.entity.json.eventdetail.running;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.Base;
import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class TagUpDetail extends RunningInfo implements EventDetail {
  private TagUpDetail(Player runner, Base startBase, Base endBase) {
    super(runner, startBase, endBase);
  }

  @Override
  public ActionType getEventType() {
    return ActionType.TAG_UP;
  }

  public static TagUpDetail of(Player runner, Base startBase, Base endBase) {
    return new TagUpDetail(runner, startBase, endBase);
  }
}
