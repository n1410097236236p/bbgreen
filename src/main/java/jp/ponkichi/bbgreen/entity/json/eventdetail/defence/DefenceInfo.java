package jp.ponkichi.bbgreen.entity.json.eventdetail.defence;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class DefenceInfo {
  private Player fielder;
  private Position position;

  protected DefenceInfo(Player fielder, Position position) {
    this.fielder = fielder;
    this.position = position;
  }
}
