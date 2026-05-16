package jp.ponkichi.bbgreen.service;

import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.User;
import jp.ponkichi.bbgreen.entity.intermediate.PlayerWatcher;
import jp.ponkichi.bbgreen.exception.ConflictException;
import jp.ponkichi.bbgreen.exception.InvalidRequestException;
import jp.ponkichi.bbgreen.exception.ResourceNotFoundException;
import jp.ponkichi.bbgreen.repository.PlayerRepository;
import jp.ponkichi.bbgreen.repository.PlayerWatcherRepository;
import jp.ponkichi.bbgreen.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerService {
  private final PlayerRepository playerRepository;
  private final UserRepository userRepository;
  private final PlayerWatcherRepository playerWatcherRepository;

  @Transactional
  public Player createPlayer(String name, String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));

    Player player = Player.of(name);
    Player savedPlayer = playerRepository.save(player);

    PlayerWatcher playerWatcher = PlayerWatcher.of(savedPlayer.getId(), user.getId());
    playerWatcherRepository.save(playerWatcher);

    return savedPlayer;
  }

  public Player getPlayerById(@NonNull Long playerId) {
    return playerRepository.findById(playerId)
        .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
  }

  public List<Player> getAllPlayers() {
    return playerRepository.findAll();
  }

  public List<Player> getPlayersByWatcherName(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));
    return playerRepository.findAllByWatcherId(user.getId());
  }

  public Player updatePlayer(Long playerId, String name) {
    Player player = playerRepository.findById(playerId)
        .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
    player.changeName(name);
    return playerRepository.save(player);
  }

  public void deletePlayer(Long playerId) {
    Player player = playerRepository.findById(playerId)
        .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
    playerRepository.delete(player);
  }

  @Transactional
  public void addWatcherToPlayer(Long playerId, String username) {
    Player player = playerRepository.findById(playerId)
        .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));

    if (playerWatcherRepository.existsById_PlayerIdAndId_UserId(player.getId(), user.getId())) {
      throw new ConflictException("User is already a watcher of this player");
    }

    PlayerWatcher playerWatcher = PlayerWatcher.of(player.getId(), user.getId());
    playerWatcherRepository.save(playerWatcher);
  }

  @Transactional
  public void removeWatcherFromPlayer(Long playerId, String username) {
    Player player = playerRepository.findById(playerId)
        .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidRequestException("User not found"));

    playerWatcherRepository.deleteById_PlayerIdAndId_UserId(player.getId(), user.getId());
  }
}
