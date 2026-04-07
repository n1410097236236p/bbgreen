package jp.ponkichi.bbgreen.entity;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team_users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamUser implements Serializable {
  @EmbeddedId
  private TeamUserId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("teamId")
  @JoinColumn(name = "team_id")
  private Team team;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private User user;

  private TeamUser(Team team, User user) {
    this.id = new TeamUserId(team.getId(), user.getId());
    this.team = team;
    this.user = user;
  }

  public static TeamUser of(Team team, User user) {
    return new TeamUser(team, user);
  }

  @Embeddable
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @Getter
  public static class TeamUserId implements Serializable {
    private Long teamId;
    private Long userId;

    public TeamUserId(Long teamId, Long userId) {
      this.teamId = teamId;
      this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      TeamUserId that = (TeamUserId) o;
      return Objects.equals(teamId, that.teamId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(teamId, userId);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    TeamUser teamUser = (TeamUser) o;
    return Objects.equals(id, teamUser.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
