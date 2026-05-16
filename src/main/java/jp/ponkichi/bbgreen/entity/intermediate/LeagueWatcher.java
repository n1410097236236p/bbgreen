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
@Table(name = "league_watchers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LeagueWatcher {

  @EmbeddedId
  private LeagueWatcherId id;

  private LeagueWatcher(Long leagueId, Long userId) {
    this.id = new LeagueWatcherId(leagueId, userId);
  }

  public static LeagueWatcher of(Long leagueId, Long userId) {
    return new LeagueWatcher(leagueId, userId);
  }

  @Embeddable
  public record LeagueWatcherId( //
      @Column(name = "league_id") Long leagueId, //
      @Column(name = "user_id") Long userId //
  ) implements Serializable {
  }
}
