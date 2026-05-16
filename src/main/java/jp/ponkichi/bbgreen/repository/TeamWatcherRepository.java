package jp.ponkichi.bbgreen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import jp.ponkichi.bbgreen.entity.intermediate.TeamWatcher;
import jp.ponkichi.bbgreen.entity.intermediate.TeamWatcher.TeamWatcherId;

public interface TeamWatcherRepository extends JpaRepository<TeamWatcher, TeamWatcherId> {
  boolean existsById_TeamIdAndId_UserId(Long teamId, Long watcherId);

  void deleteById_TeamIdAndId_UserId(Long teamId, Long watcherId);

  @Query("SELECT tw.id.userId FROM TeamWatcher tw WHERE tw.id.teamId = :teamId")
  Long[] findWatcherIdsByTeamId(Long teamId);

  @Query("SELECT tw.id.teamId FROM TeamWatcher tw WHERE tw.id.userId = :userId")
  Long[] findTeamIdsByWatcherId(Long userId);
}
