package jp.ponkichi.bbgreen.entity.json.eventdetail.atbat;

import jp.ponkichi.bbgreen.entity.constants.BattedBallType;
import jp.ponkichi.bbgreen.entity.constants.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AtBatInfo {
  private BattedBallType battedBallType;
  private Position direction;
  private Integer runBattedIn;

  protected AtBatInfo(BattedBallType battedBallType, Position direction) {
    this.battedBallType = battedBallType;
    this.direction = direction;
  }

  protected AtBatInfo(BattedBallType battedBallType, Position direction, Integer runBattedIn) {
    this.battedBallType = battedBallType;
    this.direction = direction;
    this.runBattedIn = runBattedIn;
  }

  public void setRunBattedIn(int runBattedIn) {
    if (runBattedIn < 0) {
      throw new IllegalArgumentException("runBattedIn must be non-negative");
    } else if (runBattedIn > 4) {
      throw new IllegalArgumentException("runBattedIn must be less than or equal to 4");
    }
    this.runBattedIn = runBattedIn;
  }
}
