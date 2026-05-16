package jp.ponkichi.bbgreen.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jp.ponkichi.bbgreen.entity.Season;
import jp.ponkichi.bbgreen.service.SeasonService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seasons")
@RequiredArgsConstructor
public class SeasonController {

  private final SeasonService seasonService;

  @PostMapping
  public ResponseEntity<Season> createSeason(@RequestParam String name,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
      @AuthenticationPrincipal String username) {
    Season createdSeason = seasonService.createSeason(name, startDate, endDate, username);
    return new ResponseEntity<>(createdSeason, HttpStatus.CREATED);
  }

  @GetMapping("/{seasonId}")
  public ResponseEntity<Season> getSeasonById(@PathVariable Long seasonId) {
    Season season = seasonService.getSeasonById(seasonId);
    return new ResponseEntity<>(season, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Season>> getMySeasons(@AuthenticationPrincipal String username) {
    List<Season> seasons = seasonService.getMySeasons(username);
    return new ResponseEntity<>(seasons, HttpStatus.OK);
  }

  @PutMapping("/{seasonId}")
  public ResponseEntity<Season> updateSeason(@PathVariable Long seasonId,
      @RequestParam String name, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    Season updatedSeason = seasonService.updateSeason(seasonId, name, startDate, endDate);
    return new ResponseEntity<>(updatedSeason, HttpStatus.OK);
  }

  @DeleteMapping("/{seasonId}")
  public ResponseEntity<Void> deleteSeason(@PathVariable Long seasonId) {
    seasonService.deleteSeason(seasonId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // Season watcher management endpoints
  @PostMapping("/{seasonId}/watch")
  public ResponseEntity<Void> addWatcherToSeason(@PathVariable Long seasonId,
      @AuthenticationPrincipal String username) {
    seasonService.addWatcherToSeason(seasonId, username);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{seasonId}/watch")
  public ResponseEntity<Void> removeWatcherFromSeason(@PathVariable Long seasonId,
      @AuthenticationPrincipal String username) {
    seasonService.removeWatcherFromSeason(seasonId, username);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
