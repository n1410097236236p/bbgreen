package jp.ponkichi.bbgreen.repository;

import jp.ponkichi.bbgreen.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCheckRepository extends JpaRepository<Team, Long> { //仮のジェネリクス
}
