package jp.ponkichi.bbgreen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import jp.ponkichi.bbgreen.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
