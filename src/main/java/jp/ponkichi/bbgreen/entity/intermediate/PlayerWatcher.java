package jp.ponkichi.bbgreen.entity.intermediate;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player_watchers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerWatcher {

  @EmbeddedId
  private PlayerWatcherId id;

  private PlayerWatcher(Long playerId, Long userId) {
    this.id = new PlayerWatcherId(playerId, userId);
  }

  public static PlayerWatcher of(Long playerId, Long userId) {
    return new PlayerWatcher(playerId, userId);
  }

  @Embeddable
  public record PlayerWatcherId( //
      @Column(name = "player_id") Long playerId, //
      @Column(name = "user_id") Long userId //
  ) implements Serializable {
  }
}
