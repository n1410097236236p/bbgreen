package jp.ponkichi.bbgreen.repository;

import jp.ponkichi.bbgreen.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
