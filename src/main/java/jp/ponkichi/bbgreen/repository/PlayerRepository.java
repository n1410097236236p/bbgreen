package jp.ponkichi.bbgreen.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import jp.ponkichi.bbgreen.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
  @Query("SELECT p FROM Player p JOIN PlayerWatcher pw ON p.id = pw.id.playerId WHERE pw.id.userId = :userId")
  List<Player> findAllByWatcherId(Long userId);
}
