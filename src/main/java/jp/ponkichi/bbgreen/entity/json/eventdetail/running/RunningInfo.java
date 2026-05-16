package jp.ponkichi.bbgreen.entity.json.eventdetail.running;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.constants.Base;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RunningInfo {
  private Player runner;
  private Base startBase;
  private Base endBase;

  protected RunningInfo(Player runner, Base startBase, Base endBase) {
    this.runner = runner;
    this.startBase = startBase;
    this.endBase = endBase;
  }
}
