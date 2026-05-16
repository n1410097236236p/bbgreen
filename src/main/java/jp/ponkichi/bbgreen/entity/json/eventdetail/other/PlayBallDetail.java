package jp.ponkichi.bbgreen.entity.json.eventdetail.other;

import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;

public class PlayBallDetail implements EventDetail {

  @Override
  public EventType getEventType() {
    return EventType.PLAY_BALL;
  }
}
