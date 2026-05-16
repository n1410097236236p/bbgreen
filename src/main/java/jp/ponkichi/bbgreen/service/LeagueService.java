package jp.ponkichi.bbgreen.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.ponkichi.bbgreen.entity.League;
import jp.ponkichi.bbgreen.entity.User;
import jp.ponkichi.bbgreen.entity.intermediate.LeagueWatcher;
import jp.ponkichi.bbgreen.exception.ConflictException;
import jp.ponkichi.bbgreen.exception.InvalidRequestException;
import jp.ponkichi.bbgreen.exception.ResourceNotFoundException;
import jp.ponkichi.bbgreen.repository.LeagueRepository;
import jp.ponkichi.bbgreen.repository.LeagueWatcherRepository;
import jp.ponkichi.bbgreen.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeagueService {

  private final LeagueRepository leagueRepository;
  private final UserRepository userRepository;
  private final LeagueWatcherRepository leagueWatcherRepository;

  @Transactional
  public League createLeague(String name, String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));

    League league = League.of(name);
    League savedLeague = leagueRepository.save(league);

    LeagueWatcher leagueWatcher = LeagueWatcher.of(savedLeague.getId(), user.getId());
    leagueWatcherRepository.save(leagueWatcher);

    return savedLeague;
  }

  public League getLeagueById(Long leagueId) {
    return leagueRepository.findById(leagueId)
        .orElseThrow(() -> new ResourceNotFoundException("League not found"));
  }

  public List<League> getAllLeagues() {
    return leagueRepository.findAll();
  }

  public List<League> getLeaguesByWatcherName(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));
    return leagueRepository.findAllByWatcherId(user.getId());
  }

  public League updateLeague(Long leagueId, String name) {
    League league = leagueRepository.findById(leagueId)
        .orElseThrow(() -> new ResourceNotFoundException("League not found"));
    league.changeName(name);
    return leagueRepository.save(league);
  }

  public void deleteLeague(Long leagueId) {
    League league = leagueRepository.findById(leagueId)
        .orElseThrow(() -> new ResourceNotFoundException("League not found"));
    leagueRepository.delete(league);
  }

  @Transactional
  public void addWatcherToLeague(Long leagueId, String username) {
    League league = leagueRepository.findById(leagueId)
        .orElseThrow(() -> new ResourceNotFoundException("League not found"));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));

    if (leagueWatcherRepository.existsById_LeagueIdAndId_UserId(league.getId(), user.getId())) {
      throw new ConflictException("User is already a watcher of this league");
    }

    LeagueWatcher leagueWatcher = LeagueWatcher.of(league.getId(), user.getId());
    leagueWatcherRepository.save(leagueWatcher);
  }

  @Transactional
  public void removeWatcherFromLeague(Long leagueId, String username) {
    League league = leagueRepository.findById(leagueId)
        .orElseThrow(() -> new ResourceNotFoundException("League not found"));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));

    leagueWatcherRepository.deleteById_LeagueIdAndId_UserId(league.getId(), user.getId());
  }
}
