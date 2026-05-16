package jp.ponkichi.bbgreen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import jp.ponkichi.bbgreen.entity.intermediate.LeagueWatcher;
import jp.ponkichi.bbgreen.entity.intermediate.LeagueWatcher.LeagueWatcherId;

public interface LeagueWatcherRepository extends JpaRepository<LeagueWatcher, LeagueWatcherId> {
  boolean existsById_LeagueIdAndId_UserId(Long leagueId, Long userId);

  void deleteById_LeagueIdAndId_UserId(Long leagueId, Long userId);

  @Query("SELECT lw.id.userId FROM LeagueWatcher lw WHERE lw.id.leagueId = :leagueId")
  Long[] findWatcherIdsByLeagueId(Long leagueId);

  @Query("SELECT lw.id.leagueId FROM LeagueWatcher lw WHERE lw.id.userId = :userId")
  Long[] findLeagueIdsByWatcherId(Long userId);
}
