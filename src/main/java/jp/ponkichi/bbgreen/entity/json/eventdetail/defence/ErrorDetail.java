package jp.ponkichi.bbgreen.entity.json.eventdetail.defence;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class ErrorDetail extends DefenceInfo implements EventDetail {

  private ErrorDetail(Player fielder, Position position) {
    super(fielder, position);
  }

  @Override
  public ActionType getEventType() {
    return ActionType.ERROR;
  }

  public static ErrorDetail of(Player fielder, Position position) {
    return new ErrorDetail(fielder, position);
  }

}
