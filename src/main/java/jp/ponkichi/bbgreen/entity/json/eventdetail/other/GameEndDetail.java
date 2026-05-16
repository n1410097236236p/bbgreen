package jp.ponkichi.bbgreen.entity.json.eventdetail.other;

import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameEndDetail implements EventDetail {
  private int[] topTeamScore;
  private int[] bottomTeamScore;

  @Override
  public ActionType getEventType() {
    return ActionType.GAME_END;
  }
}
