package jp.ponkichi.bbgreen.entity.json.eventdetail.other;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.EventType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import jp.ponkichi.bbgreen.entity.json.eventdetail.EventDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePositionDetail implements EventDetail {
  private Player player;
  private Position toPosition;

  private ChangePositionDetail(Player player, Position toPosition) {
    this.player = player;
    this.toPosition = toPosition;
  }

  @Override
  public EventType getEventType() {
    return EventType.CHANGE_POSITION;
  }

  public static ChangePositionDetail of(Player player, Position toPosition) {
    return new ChangePositionDetail(player, toPosition);
  }
}
