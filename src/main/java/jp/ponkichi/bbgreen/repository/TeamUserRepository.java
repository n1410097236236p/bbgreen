package jp.ponkichi.bbgreen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import jp.ponkichi.bbgreen.entity.Team;
import jp.ponkichi.bbgreen.entity.TeamUser;
import jp.ponkichi.bbgreen.entity.TeamUser.TeamUserId;
import jp.ponkichi.bbgreen.entity.User;

public interface TeamUserRepository extends JpaRepository<TeamUser, TeamUserId> {
  boolean existsByTeamAndUser(Team team, User user);

  void deleteByTeamAndUser(Team team, User user);

  @Query("SELECT tu.user.id FROM TeamUser tu WHERE tu.team.id = :teamId")
  Long[] findUserIdsByTeamId(Long teamId);
}
