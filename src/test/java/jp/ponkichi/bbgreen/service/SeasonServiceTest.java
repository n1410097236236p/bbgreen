package jp.ponkichi.bbgreen.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jp.ponkichi.bbgreen.dto.element.Password;
import jp.ponkichi.bbgreen.entity.Season;
import jp.ponkichi.bbgreen.entity.User;
import jp.ponkichi.bbgreen.entity.intermediate.SeasonWatcher;
import jp.ponkichi.bbgreen.exception.ConflictException;
import jp.ponkichi.bbgreen.exception.InvalidRequestException;
import jp.ponkichi.bbgreen.exception.ResourceNotFoundException;
import jp.ponkichi.bbgreen.repository.SeasonRepository;
import jp.ponkichi.bbgreen.repository.SeasonWatcherRepository;
import jp.ponkichi.bbgreen.repository.UserRepository;


@ExtendWith(MockitoExtension.class)
public class SeasonServiceTest {

  @Mock
  private SeasonRepository seasonRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private SeasonWatcherRepository seasonWatcherRepository;

  @InjectMocks
  private SeasonService seasonService;

  private Season testSeason;
  private User testUser;
  private SeasonWatcher testSeasonWatcher;

  @BeforeEach
  void setUp() {
    testSeason = Season.of("Test Season");
    setId(testSeason, 1L);

    testUser = User.of("testuser", new Password.Encoded("password"));
    setId(testUser, 1L);
    testSeasonWatcher = SeasonWatcher.of(testSeason.getId(), testUser.getId());
  }

  private void setId(Object entity, Long id) {
    try {
      java.lang.reflect.Field idField = entity.getClass().getDeclaredField("id");
      idField.setAccessible(true);
      idField.set(entity, id);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Failed to set ID via reflection", e);
    }
  }

  @Test
  void createSeason_shouldReturnSavedSeason() {
    String seasonName = "Test Season";
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate endDate = LocalDate.of(2023, 12, 31);
    String username = "testuser";

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
    when(seasonRepository.save(any(Season.class))).thenReturn(testSeason);
    when(seasonWatcherRepository.save(any(SeasonWatcher.class))).thenReturn(testSeasonWatcher);

    Season createdSeason = seasonService.createSeason(seasonName, startDate, endDate, username);

    assertNotNull(createdSeason);
    assertEquals(seasonName, createdSeason.getName());
    assertEquals(startDate, createdSeason.getStartDate());
    assertEquals(endDate, createdSeason.getEndDate());
    verify(userRepository, times(1)).findByUsername(username);
    verify(seasonRepository, times(1)).save(any(Season.class));
    verify(seasonWatcherRepository, times(1)).save(any(SeasonWatcher.class));
  }

  @Test
  void createSeason_shouldThrowException_whenUserNotFound() {
    String seasonName = "Test Season";
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate endDate = LocalDate.of(2023, 12, 31);
    String username = "nonExistentUser";

    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(InvalidRequestException.class,
        () -> seasonService.createSeason(seasonName, startDate, endDate, username));
    verify(userRepository, times(1)).findByUsername(username);
    verify(seasonRepository, times(0)).save(any(Season.class));
    verify(seasonWatcherRepository, times(0)).save(any(SeasonWatcher.class));
  }

  @Test
  void getSeasonById_shouldReturnSeason_whenFound() {
    when(seasonRepository.findById(1L)).thenReturn(Optional.of(testSeason));
    Season foundSeason = seasonService.getSeasonById(1L);
    assertNotNull(foundSeason);
    assertEquals("Test Season", foundSeason.getName());
  }

  @Test
  void getSeasonById_shouldThrowException_whenNotFound() {
    when(seasonRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> seasonService.getSeasonById(2L));
  }

  @Test
  void getAllSeasons_shouldReturnListOfSeasons() {
    List<Season> seasons = Arrays.asList(testSeason, Season.of("Another Season"));
    when(seasonRepository.findAll()).thenReturn(seasons);
    List<Season> foundSeasons = seasonService.getAllSeasons();
    assertNotNull(foundSeasons);
    assertEquals(2, foundSeasons.size());
  }

  @Test
  void getMySeasons_shouldReturnListOfSeasons() {
    String username = "testuser";
    List<Season> seasons = Arrays.asList(testSeason, Season.of("Another Season"));
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
    when(seasonRepository.findAllByWatcherId(testUser.getId())).thenReturn(seasons);

    List<Season> foundSeasons = seasonService.getMySeasons(username);

    assertNotNull(foundSeasons);
    assertEquals(2, foundSeasons.size());
    assertEquals("Test Season", foundSeasons.get(0).getName());
    assertEquals("Another Season", foundSeasons.get(1).getName());
    verify(userRepository, times(1)).findByUsername(username);
    verify(seasonRepository, times(1)).findAllByWatcherId(testUser.getId());
  }

  @Test
  void getMySeasons_shouldThrowException_whenUserNotFound() {
    String username = "nonExistentUser";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(InvalidRequestException.class, () -> seasonService.getMySeasons(username));
    verify(userRepository, times(1)).findByUsername(username);
    verify(seasonRepository, times(0)).findAllByWatcherId(anyLong());
  }

  @Test
  void updateSeason_shouldReturnUpdatedSeason_whenFound() {
    LocalDate newStartDate = LocalDate.of(2023, 2, 1);
    LocalDate newEndDate = LocalDate.of(2023, 11, 30);

    when(seasonRepository.findById(1L)).thenReturn(Optional.of(testSeason));
    when(seasonRepository.save(any(Season.class))).thenReturn(testSeason);

    Season updatedSeason = seasonService.updateSeason(1L, "Updated Season Name", newStartDate, newEndDate);

    assertNotNull(updatedSeason);
    assertEquals("Updated Season Name", updatedSeason.getName());
    assertEquals(newStartDate, updatedSeason.getStartDate());
    assertEquals(newEndDate, updatedSeason.getEndDate());
    verify(seasonRepository, times(1)).findById(1L);
    verify(seasonRepository, times(1)).save(any(Season.class));
  }

  @Test
  void updateSeason_shouldReturnUpdatedSeason_withPartialUpdates() {
      LocalDate newStartDate = LocalDate.of(2024, 3, 15);

      when(seasonRepository.findById(1L)).thenReturn(Optional.of(testSeason));
      when(seasonRepository.save(any(Season.class))).thenReturn(testSeason);

      Season updatedSeason = seasonService.updateSeason(1L, null, newStartDate, null);

      assertNotNull(updatedSeason);
      assertEquals("Test Season", updatedSeason.getName()); // Name should not change
      assertEquals(newStartDate, updatedSeason.getStartDate());
      assertEquals(LocalDate.of(2023, 12, 31), updatedSeason.getEndDate()); // End date should not change
      verify(seasonRepository, times(1)).findById(1L);
      verify(seasonRepository, times(1)).save(any(Season.class));
  }


  @Test
  void updateSeason_shouldThrowException_whenNotFound() {
    when(seasonRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class,
        () -> seasonService.updateSeason(2L, "New Name", null, null));
  }

  @Test
  void deleteSeason_shouldCallDelete() {
    when(seasonRepository.findById(1L)).thenReturn(Optional.of(testSeason));
    doNothing().when(seasonRepository).delete(any(Season.class));

    seasonService.deleteSeason(1L);

    verify(seasonRepository, times(1)).findById(1L);
    verify(seasonRepository, times(1)).delete(testSeason);
  }

  @Test
  void deleteSeason_shouldThrowException_whenNotFound() {
    when(seasonRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> seasonService.deleteSeason(2L));
  }

  @Test
  void addWatcherToSeason_shouldAddWatcher() {
    when(seasonRepository.findById(1L)).thenReturn(Optional.of(testSeason));
    when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
    when(seasonWatcherRepository.existsBySeasonIdAndUserId(testSeason.getId(), testUser.getId()))
        .thenReturn(false);
    when(seasonWatcherRepository.save(any(SeasonWatcher.class))).thenReturn(testSeasonWatcher);

    seasonService.addWatcherToSeason(1L, testUser.getUsername());

    verify(seasonWatcherRepository, times(1)).save(any(SeasonWatcher.class));
    verify(seasonRepository, times(1)).findById(1L);
    verify(userRepository, times(1)).findByUsername(testUser.getUsername());
    verify(seasonWatcherRepository, times(1)).existsBySeasonIdAndUserId(testSeason.getId(),
        testUser.getId());
  }

  @Test
  void addWatcherToSeason_shouldThrowException_whenSeasonNotFound() {
    when(seasonRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class,
        () -> seasonService.addWatcherToSeason(1L, "anyUser"));
  }

  @Test
  void addWatcherToSeason_shouldThrowException_whenUserNotFound() {
    when(seasonRepository.findById(1L)).thenReturn(Optional.of(testSeason));
    when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    assertThrows(InvalidRequestException.class,
        () -> seasonService.addWatcherToSeason(1L, "nonExistentUser"));
  }

  @Test
  void addWatcherToSeason_shouldThrowException_whenUserAlreadyWatcher() {
    when(seasonRepository.findById(1L)).thenReturn(Optional.of(testSeason));
    when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
    when(seasonWatcherRepository.existsBySeasonIdAndUserId(testSeason.getId(), testUser.getId()))
        .thenReturn(true);
    assertThrows(ConflictException.class,
        () -> seasonService.addWatcherToSeason(1L, testUser.getUsername()));
  }

  @Test
  void removeWatcherFromSeason_shouldRemoveWatcher() {
    when(seasonRepository.findById(1L)).thenReturn(Optional.of(testSeason));
    when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
    doNothing().when(seasonWatcherRepository).deleteBySeasonIdAndUserId(any(Long.class),
        any(Long.class));

    seasonService.removeWatcherFromSeason(1L, testUser.getUsername());

    verify(seasonWatcherRepository, times(1)).deleteBySeasonIdAndUserId(testSeason.getId(),
        testUser.getId());
    verify(seasonRepository, times(1)).findById(1L);
    verify(userRepository, times(1)).findByUsername(testUser.getUsername());
  }

  @Test
  void removeWatcherFromSeason_shouldThrowException_whenSeasonNotFound() {
    when(seasonRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class,
        () -> seasonService.removeWatcherFromSeason(1L, "anyUser"));
  }

  @Test
  void removeWatcherFromSeason_shouldThrowException_whenUserNotFound() {
    when(seasonRepository.findById(1L)).thenReturn(Optional.of(testSeason));
    when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    assertThrows(InvalidRequestException.class,
        () -> seasonService.removeWatcherFromSeason(1L, "nonExistentUser"));
  }
}
