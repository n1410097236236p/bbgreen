package jp.ponkichi.bbgreen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import jp.ponkichi.bbgreen.entity.Season;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface SeasonRepository extends JpaRepository<Season, Long> {
  @Query("SELECT s FROM Season s JOIN SeasonWatcher sw ON s.id = sw.id.seasonId WHERE sw.id.userId = :userId")
  List<Season> findAllByWatcherId(Long userId);
}

