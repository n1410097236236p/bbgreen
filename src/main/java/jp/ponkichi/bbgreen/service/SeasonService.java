package jp.ponkichi.bbgreen.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.ponkichi.bbgreen.entity.Season;
import jp.ponkichi.bbgreen.entity.User;
import jp.ponkichi.bbgreen.entity.intermediate.SeasonWatcher;
import jp.ponkichi.bbgreen.exception.ConflictException;
import jp.ponkichi.bbgreen.exception.InvalidRequestException;
import jp.ponkichi.bbgreen.exception.ResourceNotFoundException;
import jp.ponkichi.bbgreen.repository.SeasonRepository;
import jp.ponkichi.bbgreen.repository.SeasonWatcherRepository;
import jp.ponkichi.bbgreen.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeasonService {

  private final SeasonRepository seasonRepository;
  private final UserRepository userRepository;
  private final SeasonWatcherRepository seasonWatcherRepository;

  @Transactional
  public Season createSeason(String name, LocalDate startDate, LocalDate endDate, String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));

    Season season = Season.of(name);
    season.setStartDate(startDate);
    season.setEndDate(endDate);
    Season savedSeason = seasonRepository.save(season);

    SeasonWatcher seasonWatcher = SeasonWatcher.of(savedSeason.getId(), user.getId());
    seasonWatcherRepository.save(seasonWatcher);

    return savedSeason;
  }

  public Season getSeasonById(@NonNull Long seasonId) {
    return seasonRepository.findById(seasonId)
        .orElseThrow(() -> new ResourceNotFoundException("Season not found"));
  }

  public List<Season> getAllSeasons() {
    return seasonRepository.findAll();
  }

  public List<Season> getMySeasons(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));
    return seasonRepository.findAllByWatcherId(user.getId());
  }

  public Season updateSeason(Long seasonId, String name, LocalDate startDate, LocalDate endDate) {
    Season season = seasonRepository.findById(seasonId)
        .orElseThrow(() -> new ResourceNotFoundException("Season not found"));
    if (name != null)
      season.changeName(name);
    if (startDate != null)
      season.setStartDate(startDate);
    if (endDate != null)
      season.setEndDate(endDate);
    return seasonRepository.save(season);
  }

  public void deleteSeason(Long seasonId) {
    Season season = seasonRepository.findById(seasonId)
        .orElseThrow(() -> new ResourceNotFoundException("Season not found"));
    seasonRepository.delete(season);
  }

  @Transactional
  public void addWatcherToSeason(Long seasonId, String username) {
    Season season = seasonRepository.findById(seasonId)
        .orElseThrow(() -> new ResourceNotFoundException("Season not found"));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));

    if (seasonWatcherRepository.existsById_SeasonIdAndId_UserId(season.getId(), user.getId())) {
      throw new ConflictException("User is already a watcher of this season");
    }

    SeasonWatcher seasonWatcher = SeasonWatcher.of(season.getId(), user.getId());
    seasonWatcherRepository.save(seasonWatcher);
  }

  @Transactional
  public void removeWatcherFromSeason(Long seasonId, String username) {
    Season season = seasonRepository.findById(seasonId)
        .orElseThrow(() -> new ResourceNotFoundException("Season not found"));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));

    seasonWatcherRepository.deleteById_SeasonIdAndId_UserId(season.getId(), user.getId());
  }
}
