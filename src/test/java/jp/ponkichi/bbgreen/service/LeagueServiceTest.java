package jp.ponkichi.bbgreen.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import jp.ponkichi.bbgreen.entity.League;
import jp.ponkichi.bbgreen.entity.User;
import jp.ponkichi.bbgreen.entity.intermediate.LeagueWatcher;
import jp.ponkichi.bbgreen.exception.ConflictException;
import jp.ponkichi.bbgreen.exception.InvalidRequestException;
import jp.ponkichi.bbgreen.exception.ResourceNotFoundException;
import jp.ponkichi.bbgreen.repository.LeagueRepository;
import jp.ponkichi.bbgreen.repository.LeagueWatcherRepository;
import jp.ponkichi.bbgreen.repository.UserRepository;


@ExtendWith(MockitoExtension.class)
public class LeagueServiceTest {

  @Mock
  private LeagueRepository leagueRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private LeagueWatcherRepository leagueWatcherRepository;

  @InjectMocks
  private LeagueService leagueService;

  private League testLeague;
  private User testUser;
  private LeagueWatcher testLeagueWatcher;

  @BeforeEach
  void setUp() {
    testLeague = League.of("Test League");
    setId(testLeague, 1L);

    testUser = User.of("testuser", new Password.Encoded("password"));
    setId(testUser, 1L);
    testLeagueWatcher = LeagueWatcher.of(testLeague.getId(), testUser.getId());
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
  void createLeague_shouldReturnSavedLeague() {
    String leagueName = "Test League";
    String username = "testuser";

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
    when(leagueRepository.save(any(League.class))).thenReturn(testLeague);
    when(leagueWatcherRepository.save(any(LeagueWatcher.class))).thenReturn(testLeagueWatcher);

    League createdLeague = leagueService.createLeague(leagueName, username);

    assertNotNull(createdLeague);
    assertEquals(leagueName, createdLeague.getName());
    verify(userRepository, times(1)).findByUsername(username);
    verify(leagueRepository, times(1)).save(any(League.class));
    verify(leagueWatcherRepository, times(1)).save(any(LeagueWatcher.class));
  }

  @Test
  void createLeague_shouldThrowException_whenUserNotFound() {
    String leagueName = "Test League";
    String username = "nonExistentUser";

    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(InvalidRequestException.class,
        () -> leagueService.createLeague(leagueName, username));
    verify(userRepository, times(1)).findByUsername(username);
    verify(leagueRepository, times(0)).save(any(League.class));
    verify(leagueWatcherRepository, times(0)).save(any(LeagueWatcher.class));
  }

  @Test
  void getLeagueById_shouldReturnLeague_whenFound() {
    when(leagueRepository.findById(1L)).thenReturn(Optional.of(testLeague));
    League foundLeague = leagueService.getLeagueById(1L);
    assertNotNull(foundLeague);
    assertEquals("Test League", foundLeague.getName());
  }

  @Test
  void getLeagueById_shouldThrowException_whenNotFound() {
    when(leagueRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> leagueService.getLeagueById(2L));
  }

  @Test
  void getAllLeagues_shouldReturnListOfLeagues() {
    List<League> leagues = Arrays.asList(testLeague, League.of("Another League"));
    when(leagueRepository.findAll()).thenReturn(leagues);
    List<League> foundLeagues = leagueService.getAllLeagues();
    assertNotNull(foundLeagues);
    assertEquals(2, foundLeagues.size());
  }

  @Test
  void getLeaguesByWatcherName_shouldReturnListOfLeagues() {
    String username = "testuser";
    List<League> leagues = Arrays.asList(testLeague, League.of("Another League"));
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
    when(leagueRepository.findAllByWatcherId(testUser.getId())).thenReturn(leagues);

    List<League> foundLeagues = leagueService.getLeaguesByWatcherName(username);

    assertNotNull(foundLeagues);
    assertEquals(2, foundLeagues.size());
    assertEquals("Test League", foundLeagues.get(0).getName());
    assertEquals("Another League", foundLeagues.get(1).getName());
    verify(userRepository, times(1)).findByUsername(username);
    verify(leagueRepository, times(1)).findAllByWatcherId(testUser.getId());
  }

  @Test
  void getLeaguesByWatcherName_shouldThrowException_whenUserNotFound() {
    String username = "nonExistentUser";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(InvalidRequestException.class, () -> leagueService.getLeaguesByWatcherName(username));
    verify(userRepository, times(1)).findByUsername(username);
    verify(leagueRepository, times(0)).findAllByWatcherId(anyLong());
  }

  @Test
  void updateLeague_shouldReturnUpdatedLeague_whenFound() {
    when(leagueRepository.findById(1L)).thenReturn(Optional.of(testLeague));
    when(leagueRepository.save(any(League.class))).thenReturn(testLeague);
    League updatedLeague = leagueService.updateLeague(1L, "Updated League Name");
    assertNotNull(updatedLeague);
    assertEquals("Updated League Name", updatedLeague.getName());
  }

  @Test
  void updateLeague_shouldThrowException_whenNotFound() {
    when(leagueRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class,
        () -> leagueService.updateLeague(2L, "New Name"));
  }

  @Test
  void deleteLeague_shouldCallDelete() {
    when(leagueRepository.findById(1L)).thenReturn(Optional.of(testLeague));
    doNothing().when(leagueRepository).delete(any(League.class));

    leagueService.deleteLeague(1L);

    verify(leagueRepository, times(1)).findById(1L);
    verify(leagueRepository, times(1)).delete(testLeague);
  }

  @Test
  void deleteLeague_shouldThrowException_whenNotFound() {
    when(leagueRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> leagueService.deleteLeague(2L));
  }

  @Test
  void addWatcherToLeague_shouldAddWatcher() {
    when(leagueRepository.findById(1L)).thenReturn(Optional.of(testLeague));
    when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
    when(leagueWatcherRepository.existsByLeagueIdAndUserId(testLeague.getId(), testUser.getId()))
        .thenReturn(false);
    when(leagueWatcherRepository.save(any(LeagueWatcher.class))).thenReturn(testLeagueWatcher);

    leagueService.addWatcherToLeague(1L, testUser.getUsername());

    verify(leagueWatcherRepository, times(1)).save(any(LeagueWatcher.class));
    verify(leagueRepository, times(1)).findById(1L);
    verify(userRepository, times(1)).findByUsername(testUser.getUsername());
    verify(leagueWatcherRepository, times(1)).existsByLeagueIdAndUserId(testLeague.getId(),
        testUser.getId());
  }

  @Test
  void addWatcherToLeague_shouldThrowException_whenLeagueNotFound() {
    when(leagueRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class,
        () -> leagueService.addWatcherToLeague(1L, "anyUser"));
  }

  @Test
  void addWatcherToLeague_shouldThrowException_whenUserNotFound() {
    when(leagueRepository.findById(1L)).thenReturn(Optional.of(testLeague));
    when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    assertThrows(InvalidRequestException.class,
        () -> leagueService.addWatcherToLeague(1L, "nonExistentUser"));
  }

  @Test
  void addWatcherToLeague_shouldThrowException_whenUserAlreadyWatcher() {
    when(leagueRepository.findById(1L)).thenReturn(Optional.of(testLeague));
    when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
    when(leagueWatcherRepository.existsByLeagueIdAndUserId(testLeague.getId(), testUser.getId()))
        .thenReturn(true);
    assertThrows(ConflictException.class,
        () -> leagueService.addWatcherToLeague(1L, testUser.getUsername()));
  }

  @Test
  void removeWatcherFromLeague_shouldRemoveWatcher() {
    when(leagueRepository.findById(1L)).thenReturn(Optional.of(testLeague));
    when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
    doNothing().when(leagueWatcherRepository).deleteByLeagueIdAndUserId(any(Long.class),
        any(Long.class));

    leagueService.removeWatcherFromLeague(1L, testUser.getUsername());

    verify(leagueWatcherRepository, times(1)).deleteByLeagueIdAndUserId(testLeague.getId(),
        testUser.getId());
    verify(leagueRepository, times(1)).findById(1L);
    verify(userRepository, times(1)).findByUsername(testUser.getUsername());
  }

  @Test
  void removeWatcherFromLeague_shouldThrowException_whenLeagueNotFound() {
    when(leagueRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class,
        () -> leagueService.removeWatcherFromLeague(1L, "anyUser"));
  }

  @Test
  void removeWatcherFromLeague_shouldThrowException_whenUserNotFound() {
    when(leagueRepository.findById(1L)).thenReturn(Optional.of(testLeague));
    when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    assertThrows(InvalidRequestException.class,
        () -> leagueService.removeWatcherFromLeague(1L, "nonExistentUser"));
  }
}
