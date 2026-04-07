package jp.ponkichi.bbgreen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.ponkichi.bbgreen.entity.Team;

@Repository
public interface HealthCheckRepository extends JpaRepository<Team, Long> { // 仮のジェネリクス
}
