package jp.ponkichi.bbgreen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jp.ponkichi.bbgreen.entity.intermediate.PlayerWatcher;
import jp.ponkichi.bbgreen.entity.intermediate.PlayerWatcher.PlayerWatcherId;

public interface PlayerWatcherRepository extends JpaRepository<PlayerWatcher, PlayerWatcherId> {
  boolean existsById_PlayerIdAndId_UserId(Long playerId, Long userId);

  void deleteById_PlayerIdAndId_UserId(Long playerId, Long userId);

  @Query("SELECT pw.id.userId FROM PlayerWatcher pw WHERE pw.id.playerId = :playerId")
  Long[] findWatcherIdsByPlayerId(Long playerId);

  @Query("SELECT pw.id.playerId FROM PlayerWatcher pw WHERE pw.id.userId = :userId")
  Long[] findPlayerIdsByUserId(Long userId);
}
