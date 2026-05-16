package jp.ponkichi.bbgreen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import jp.ponkichi.bbgreen.entity.League;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface LeagueRepository extends JpaRepository<League, Long> {
  @Query("SELECT l FROM League l JOIN LeagueWatcher lw ON l.id = lw.id.leagueId WHERE lw.id.userId = :userId")
  List<League> findAllByWatcherId(Long userId);
}

