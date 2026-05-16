package jp.ponkichi.bbgreen.service;

import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.ponkichi.bbgreen.entity.Team;
import jp.ponkichi.bbgreen.entity.User;
import jp.ponkichi.bbgreen.entity.intermediate.TeamWatcher;
import jp.ponkichi.bbgreen.exception.ConflictException;
import jp.ponkichi.bbgreen.exception.InvalidRequestException;
import jp.ponkichi.bbgreen.exception.ResourceNotFoundException;
import jp.ponkichi.bbgreen.repository.TeamRepository;
import jp.ponkichi.bbgreen.repository.TeamWatcherRepository;
import jp.ponkichi.bbgreen.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {
  private final TeamRepository teamRepository;
  private final UserRepository userRepository;
  private final TeamWatcherRepository teamWatcherRepository;

  @Transactional
  public Team createTeam(String name, String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));

    Team team = Team.of(name);
    Team savedTeam = teamRepository.save(team);

    TeamWatcher teamWatcher = TeamWatcher.of(savedTeam.getId(), user.getId());
    teamWatcherRepository.save(teamWatcher);

    return savedTeam;
  }

  public Team getTeamById(@NonNull Long teamId) {
    return teamRepository.findById(teamId)
        .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
  }

  public List<Team> getAllTeams() {
    return teamRepository.findAll();
  }

  public List<Team> getTeamsByWatcherName(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));
    return teamRepository.findAllByWatcherId(user.getId());
  }

  public Team updateTeam(Long teamId, String name) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    team.changeName(name);
    return teamRepository.save(team);
  }

  public void deleteTeam(Long teamId) {
    teamRepository.deleteById(teamId);
  }

  @Transactional
  public void addWatcherToTeam(Long teamId, String username) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));

    if (teamWatcherRepository.existsByTeamAndWatcher(team.getId(), user.getId())) {
      throw new ConflictException("User is already a watcher of this team");
    }

    TeamWatcher teamWatcher = TeamWatcher.of(team.getId(), user.getId());
    teamWatcherRepository.save(teamWatcher);
  }

  @Transactional
  public void removeWatcherFromTeam(Long teamId, String username) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));

    teamWatcherRepository.deleteByTeamAndWatcher(team.getId(), user.getId());
  }
}
