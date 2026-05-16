package jp.ponkichi.bbgreen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import jp.ponkichi.bbgreen.entity.intermediate.TeamWatcher;
import jp.ponkichi.bbgreen.entity.intermediate.TeamWatcher.TeamWatcherId;

public interface TeamWatcherRepository extends JpaRepository<TeamWatcher, TeamWatcherId> {
  boolean existsByTeamAndWatcher(Long teamId, Long watcherId);

  void deleteByTeamAndWatcher(Long teamId, Long watcherId);

  @Query("SELECT tw.user.id FROM TeamWatcher tw WHERE tw.team.id = :teamId")
  Long[] findWatcherIdsByTeamId(Long teamId);

  @Query("SELECT tw.team.id FROM TeamWatcher tw WHERE tw.watcher.id = :userId")
  Long[] findTeamIdsByWatcherId(Long userId);
}
