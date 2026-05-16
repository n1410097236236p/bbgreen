package jp.ponkichi.bbgreen.entity.json.eventdetail.other;

import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameEndDetail implements EventDetail {
  private int[] topTeamScore;
  private int[] bottomTeamScore;

  @Override
  public EventType getEventType() {
    return EventType.GAME_END;
  }
}
