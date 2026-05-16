package jp.ponkichi.bbgreen.entity.json.eventdetail.other;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.ActionType;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePlayerDetail implements EventDetail {
  private Player outPlayer;
  private Player inPlayer;

  private ChangePlayerDetail(Player outPlayer, Player inPlayer) {
    this.outPlayer = outPlayer;
    this.inPlayer = inPlayer;
  }

  @Override
  public ActionType getEventType() {
    return ActionType.CHANGE_PLAYER;
  }

  public static ChangePlayerDetail of(Player outPlayer, Player inPlayer) {
    return new ChangePlayerDetail(outPlayer, inPlayer);
  }
}
