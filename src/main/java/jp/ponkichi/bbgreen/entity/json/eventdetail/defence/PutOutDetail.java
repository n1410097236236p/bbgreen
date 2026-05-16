package jp.ponkichi.bbgreen.entity.json.eventdetail.defence;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class PutOutDetail extends DefenceInfo implements EventDetail {

  private PutOutDetail(Player fielder, Position position) {
    super(fielder, position);
  }

  @Override
  public ActionType getEventType() {
    return ActionType.PUT_OUT;
  }

  public static PutOutDetail of(Player fielder, Position position) {
    return new PutOutDetail(fielder, position);
  }

}
