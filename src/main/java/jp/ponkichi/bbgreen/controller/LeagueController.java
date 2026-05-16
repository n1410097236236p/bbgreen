package jp.ponkichi.bbgreen.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jp.ponkichi.bbgreen.entity.League;
import jp.ponkichi.bbgreen.service.LeagueService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/leagues")
@RequiredArgsConstructor
public class LeagueController {

  private final LeagueService leagueService;

  @PostMapping
  public ResponseEntity<League> createLeague(@RequestParam String name,
      @AuthenticationPrincipal String username) {
    League createdLeague = leagueService.createLeague(name, username);
    return new ResponseEntity<>(createdLeague, HttpStatus.CREATED);
  }

  @GetMapping("/{leagueId}")
  public ResponseEntity<League> getLeagueById(@PathVariable Long leagueId) {
    League league = leagueService.getLeagueById(leagueId);
    return new ResponseEntity<>(league, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<League>> getMyLeagues(@AuthenticationPrincipal String username) {
    List<League> leagues = leagueService.getLeaguesByWatcherName(username);
    return new ResponseEntity<>(leagues, HttpStatus.OK);
  }

  @PutMapping("/{leagueId}")
  public ResponseEntity<League> updateLeague(@PathVariable Long leagueId,
      @RequestParam String name) {
    League updatedLeague = leagueService.updateLeague(leagueId, name);
    return new ResponseEntity<>(updatedLeague, HttpStatus.OK);
  }

  @DeleteMapping("/{leagueId}")
  public ResponseEntity<Void> deleteLeague(@PathVariable Long leagueId) {
    leagueService.deleteLeague(leagueId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // League watcher management endpoints
  @PostMapping("/{leagueId}/watch")
  public ResponseEntity<Void> addWatcherToLeague(@PathVariable Long leagueId,
      @AuthenticationPrincipal String username) {
    leagueService.addWatcherToLeague(leagueId, username);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{leagueId}/watch")
  public ResponseEntity<Void> removeWatcherFromLeague(@PathVariable Long leagueId,
      @AuthenticationPrincipal String username) {
    leagueService.removeWatcherFromLeague(leagueId, username);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
