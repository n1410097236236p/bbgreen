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
@Table(name = "team_watchers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamWatcher {

  @EmbeddedId
  private TeamWatcherId id;

  private TeamWatcher(Long teamId, Long userId) {
    this.id = new TeamWatcherId(teamId, userId);
  }

  public static TeamWatcher of(Long teamId, Long userId) {
    return new TeamWatcher(teamId, userId);
  }

  @Embeddable
  public record TeamWatcherId( //
      @Column(name = "team_id") Long teamId, //
      @Column(name = "user_id") Long userId //
  ) implements Serializable {
  }
}
