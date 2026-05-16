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
@Table(name = "season_watchers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeasonWatcher {

  @EmbeddedId
  private SeasonWatcherId id;

  private SeasonWatcher(Long seasonId, Long userId) {
    this.id = new SeasonWatcherId(seasonId, userId);
  }

  public static SeasonWatcher of(Long seasonId, Long userId) {
    return new SeasonWatcher(seasonId, userId);
  }

  @Embeddable
  public record SeasonWatcherId( //
      @Column(name = "season_id") Long seasonId, //
      @Column(name = "user_id") Long userId //
  ) implements Serializable {
  }
}
