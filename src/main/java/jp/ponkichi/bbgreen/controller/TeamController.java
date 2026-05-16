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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jp.ponkichi.bbgreen.entity.Team;
import jp.ponkichi.bbgreen.service.TeamService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

  private final TeamService teamService;

  @PostMapping
  public ResponseEntity<Team> createTeam(@RequestParam String name,
      @AuthenticationPrincipal String username) {
    Team createdTeam = teamService.createTeam(name, username);
    return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
  }

  @GetMapping("/{teamId}")
  public ResponseEntity<Team> getTeamById(@PathVariable Long teamId) {
    Team team = teamService.getTeamById(teamId);
    return new ResponseEntity<>(team, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Team>> getMyTeams(@AuthenticationPrincipal String username) {
    List<Team> teams = teamService.getTeamsByWatcherName(username);
    return new ResponseEntity<>(teams, HttpStatus.OK);
  }

  @PutMapping("/{teamId}")
  public ResponseEntity<Team> updateTeam(@PathVariable Long teamId, @RequestParam String name) {
    Team updatedTeam = teamService.updateTeam(teamId, name);
    return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
  }

  @DeleteMapping("/{teamId}")
  public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
    teamService.deleteTeam(teamId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // Team watcher management endpoints
  @PostMapping("/{teamId}/watch")
  public ResponseEntity<Void> addWatcherToTeam(@PathVariable Long teamId,
      @AuthenticationPrincipal String username) {
    teamService.addWatcherToTeam(teamId, username);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{teamId}/watch")
  public ResponseEntity<Void> removeWatcherFromTeam(@PathVariable Long teamId,
      @AuthenticationPrincipal String username) {
    teamService.removeWatcherFromTeam(teamId, username);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
