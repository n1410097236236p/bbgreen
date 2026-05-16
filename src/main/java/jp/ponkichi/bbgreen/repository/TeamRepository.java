package jp.ponkichi.bbgreen.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import jp.ponkichi.bbgreen.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
  @Query("SELECT t FROM Team t JOIN TeamWatcher m ON t.id = m.id.teamId WHERE m.id.userId = :userId")
  List<Team> findAllByWatcherId(Long userId);
}
