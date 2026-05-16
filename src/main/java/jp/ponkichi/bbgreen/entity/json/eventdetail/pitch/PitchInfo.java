package jp.ponkichi.bbgreen.entity.json.eventdetail.pitch;

import jp.ponkichi.bbgreen.entity.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PitchInfo {
  private Player pitcher;
  private Player batter;

  private Double speed; // km/h
  private String pitchType; // e.g., "Fastball", "Curveball", etc
  private Integer horizontalLocation; // -100 (right) to 100 (left), 0 is straight
  private Integer verticalLocation; // -100 (down) to 100 (up), 0 is straight

  protected PitchInfo(Player pitcher, Player batter) {
    this.pitcher = pitcher;
    this.batter = batter;
  }
}
