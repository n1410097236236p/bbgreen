package jp.ponkichi.bbgreen.healthcheck;

import jp.ponkichi.bbgreen.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCheckRepository extends JpaRepository<Team, Long> { //仮のジェネリクス
}
