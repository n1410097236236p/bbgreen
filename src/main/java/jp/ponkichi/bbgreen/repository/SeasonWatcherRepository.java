package jp.ponkichi.bbgreen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import jp.ponkichi.bbgreen.entity.intermediate.SeasonWatcher;
import jp.ponkichi.bbgreen.entity.intermediate.SeasonWatcher.SeasonWatcherId;

public interface SeasonWatcherRepository extends JpaRepository<SeasonWatcher, SeasonWatcherId> {
  boolean existsById_SeasonIdAndId_UserId(Long seasonId, Long userId);

  void deleteById_SeasonIdAndId_UserId(Long seasonId, Long userId);

  @Query("SELECT sw.id.userId FROM SeasonWatcher sw WHERE sw.id.seasonId = :seasonId")
  Long[] findWatcherIdsBySeasonId(Long seasonId);

  @Query("SELECT sw.id.seasonId FROM SeasonWatcher sw WHERE sw.id.userId = :userId")
  Long[] findSeasonIdsByWatcherId(Long userId);
}
