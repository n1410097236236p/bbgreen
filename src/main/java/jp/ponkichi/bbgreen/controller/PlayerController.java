package jp.ponkichi.bbgreen.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jp.ponkichi.bbgreen.dto.PlayerRequest;
import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.service.PlayerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

  private final PlayerService playerService;

  @PostMapping
  public ResponseEntity<Player> createPlayer(@RequestBody PlayerRequest request,
      @AuthenticationPrincipal String username) {
    Player createdPlayer = playerService.createPlayer(request.name(), username);
    return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
  }

  @GetMapping("/{playerId}")
  public ResponseEntity<Player> getPlayerById(@PathVariable Long playerId) {
    Player player = playerService.getPlayerById(playerId);
    return new ResponseEntity<>(player, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Player>> getMyPlayers(@AuthenticationPrincipal String username) {
    List<Player> players = playerService.getPlayersByWatcherName(username);
    return new ResponseEntity<>(players, HttpStatus.OK);
  }

  @PutMapping("/{playerId}")
  public ResponseEntity<Player> updatePlayer(@PathVariable Long playerId,
      @RequestBody PlayerRequest request) {
    Player updatedPlayer = playerService.updatePlayer(playerId, request.name());
    return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
  }

  @DeleteMapping("/{playerId}")
  public ResponseEntity<Void> deletePlayer(@PathVariable Long playerId) {
    playerService.deletePlayer(playerId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // Player watcher management endpoints
  @PostMapping("/{playerId}/watch")
  public ResponseEntity<Void> addWatcherToPlayer(@PathVariable Long playerId,
      @AuthenticationPrincipal String username) {
    playerService.addWatcherToPlayer(playerId, username);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{playerId}/watch")
  public ResponseEntity<Void> removeWatcherFromPlayer(@PathVariable Long playerId,
      @AuthenticationPrincipal String username) {
    playerService.removeWatcherFromPlayer(playerId, username);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
