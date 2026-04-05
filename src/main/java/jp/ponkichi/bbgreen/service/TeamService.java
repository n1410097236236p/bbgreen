package jp.ponkichi.bbgreen.service;

import jp.ponkichi.bbgreen.repository.TeamRepository;
import jp.ponkichi.bbgreen.entity.Team;
import jp.ponkichi.bbgreen.entity.TeamUser;
import jp.ponkichi.bbgreen.entity.User;
import jp.ponkichi.bbgreen.repository.TeamUserRepository;
import jp.ponkichi.bbgreen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamUserRepository teamUserRepository;

    @Transactional
    public Team createTeam(String name, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Team team = Team.of(name);
        Team savedTeam = teamRepository.save(team);

        TeamUser teamUser = TeamUser.of(savedTeam, user);
        teamUserRepository.save(teamUser);

        return savedTeam;
    }

    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElse(null);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team updateTeam(Long teamId, String name) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found"));
        team.changeName(name);
        return teamRepository.save(team);
    }

    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }

    @Transactional
    public void addWatcherToTeam(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (teamUserRepository.existsByTeamAndUser(team, user)) {
            throw new IllegalArgumentException("User is already a watcher of this team");
        }

        TeamUser teamUser = TeamUser.of(team, user);
        teamUserRepository.save(teamUser);
    }

    @Transactional
    public void removeWatcherFromTeam(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        teamUserRepository.deleteByTeamAndUser(team, user);
    }

    public Long[] getTeamWatchers(Long teamId) {
        return teamUserRepository.findUserIdsByTeamId(teamId);
    }

}
