package jp.ponkichi.bbgreen.entity.json.eventdetail.other;

import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class PlayBallDetail implements EventDetail {

  @Override
  public ActionType getEventType() {
    return ActionType.PLAY_BALL;
  }
}
