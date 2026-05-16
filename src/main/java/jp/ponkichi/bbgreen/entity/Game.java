package jp.ponkichi.bbgreen.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jp.ponkichi.bbgreen.entity.constants.GameStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "games")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false, name = "season_id")
  @Setter
  private Season season;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false, name = "league_id")
  @Setter
  private League league;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "top_team_id")
  @Setter
  private Team topTeam;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bottom_team_id")
  @Setter
  private Team bottomTeam;

  @Enumerated(EnumType.STRING)
  @Column(name = "current_status", nullable = false, length = 63)
  @Setter
  private GameStatus currentStatus;

  @Column(name = "game_start_time")
  @Setter
  private LocalDateTime gameStartTime;

  @Column(columnDefinition = "TEXT")
  @Setter
  private String description;

  @Column(nullable = false, name = "current_sequence_number")
  private Long currentSequenceNumber;

  private Game(Season season, League league) {
    this.season = season;
    this.league = league;
    this.currentSequenceNumber = 0L;
  }

  public static Game of(Season season, League league) {
    if (season == null) {
      throw new IllegalArgumentException("season is required");
    }
    if (league == null) {
      throw new IllegalArgumentException("league is required");
    }
    return new Game(season, league);
  }

  public Long incrementSequenceNumber() {
    this.currentSequenceNumber++;
    return this.currentSequenceNumber;
  }
}
