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
    if (team != null) {
      return new ResponseEntity<>(team, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping
  public ResponseEntity<List<Team>> getAllTeams() {
    List<Team> teams = teamService.getAllTeams();
    return new ResponseEntity<>(teams, HttpStatus.OK);
  }

  @PutMapping("/{teamId}")
  public ResponseEntity<Team> updateTeam(@PathVariable Long teamId, @RequestParam String name) {
    try {
      Team updatedTeam = teamService.updateTeam(teamId, name);
      return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{teamId}")
  public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
    teamService.deleteTeam(teamId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // Team watcher management endpoints
  @PostMapping("/{teamId}/watchers")
  public ResponseEntity<Void> addWatcherToTeam(@PathVariable Long teamId,
      @RequestParam Long userId) {
    try {
      teamService.addWatcherToTeam(teamId, userId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{teamId}/watchers/{userId}")
  public ResponseEntity<Void> removeWatcherFromTeam(@PathVariable Long teamId,
      @PathVariable Long userId) {
    try {
      teamService.removeWatcherFromTeam(teamId, userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/{teamId}/watchers")
  public ResponseEntity<Long[]> getTeamWatchers(@PathVariable Long teamId) {
    Long[] watcherIds = teamService.getTeamWatchers(teamId);
    return new ResponseEntity<>(watcherIds, HttpStatus.OK);
  }
}
