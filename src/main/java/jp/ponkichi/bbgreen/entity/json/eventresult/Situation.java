package jp.ponkichi.bbgreen.entity.json.eventresult;

import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.exception.InvalidRequestException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Situation {
  private int inning;
  private boolean isTop;
  private int batterOrder; // 1-9, or 0 if not applicable
  private int ballCount;
  private int strikeCount;
  private int outCount;

  private Player firstBaseRunner;
  private Player secondBaseRunner;
  private Player thirdBaseRunner;

  private Situation(int inning, boolean isTop, int batterOrder, int ballCount, int strikeCount,
      int outCount) {
    this.inning = inning;
    this.isTop = isTop;
    this.batterOrder = batterOrder;
    this.ballCount = ballCount;
    this.strikeCount = strikeCount;
    this.outCount = outCount;
  }

  public static Situation of(int inning, boolean isTop, int batterOrder, int ballCount,
      int strikeCount, int outCount) {
    if (inning < 1) {
      throw new InvalidRequestException("Inning must be a positive integer");
    }
    if (batterOrder < 0 || batterOrder > 9) {
      throw new InvalidRequestException("Batter order must be between 0 and 9");
    }
    if (ballCount < 0 || ballCount > 4) {
      throw new InvalidRequestException("Ball count must be between 0 and 4");
    }
    if (strikeCount < 0 || strikeCount > 3) {
      throw new InvalidRequestException("Strike count must be between 0 and 3");
    }
    if (outCount < 0 || outCount > 3) {
      throw new InvalidRequestException("Out count must be between 0 and 3");
    }
    return new Situation(inning, isTop, batterOrder, ballCount, strikeCount, outCount);
  }
}
