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
import jp.ponkichi.bbgreen.entity.Team;
import jp.ponkichi.bbgreen.entity.User;
import jp.ponkichi.bbgreen.entity.intermediate.TeamWatcher;
import jp.ponkichi.bbgreen.exception.ConflictException;
import jp.ponkichi.bbgreen.exception.InvalidRequestException;
import jp.ponkichi.bbgreen.exception.ResourceNotFoundException;
import jp.ponkichi.bbgreen.repository.TeamRepository;
import jp.ponkichi.bbgreen.repository.TeamWatcherRepository;
import jp.ponkichi.bbgreen.repository.UserRepository;


@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

  @Mock
  private TeamRepository teamRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private TeamWatcherRepository teamWatcherRepository;

  @InjectMocks
  private TeamService teamService;

  private Team testTeam;
  private User testUser;
  private TeamWatcher testWatcher;

  @BeforeEach
  void setUp() {
    testTeam = Team.of("Test Team");
    setId(testTeam, 1L);

    testUser = User.of("testuser", new Password.Encoded("password"));
    setId(testUser, 1L);
    testWatcher = TeamWatcher.of(testTeam.getId(), testUser.getId());
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
  void createTeam_shouldReturnSavedTeam() {
    String teamName = "Test Team";
    String username = "testuser";

    // Mock the repository methods
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
    when(teamRepository.save(any(Team.class))).thenReturn(testTeam);
    when(teamWatcherRepository.save(any(TeamWatcher.class))).thenReturn(testWatcher);

    Team createdTeam = teamService.createTeam(teamName, username);

    assertNotNull(createdTeam);
    assertEquals(teamName, createdTeam.getName());
    verify(userRepository, times(1)).findByUsername(username);
    verify(teamRepository, times(1)).save(any(Team.class));
    verify(teamWatcherRepository, times(1)).save(any(TeamWatcher.class));
  }

  @Test
  void getTeamById_shouldReturnTeam_whenFound() {
    when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
    Team foundTeam = teamService.getTeamById(1L);
    assertNotNull(foundTeam);
    assertEquals("Test Team", foundTeam.getName());
  }

  @Test
  void getTeamById_shouldThrowException_whenNotFound() {
    when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> teamService.getTeamById(2L));
  }

  @Test
  void getAllTeams_shouldReturnListOfTeams() {
    List<Team> teams = Arrays.asList(testTeam, Team.of("Another Team"));
    when(teamRepository.findAll()).thenReturn(teams);
    List<Team> foundTeams = teamService.getAllTeams();
    assertNotNull(foundTeams);
    assertEquals(2, foundTeams.size());
  }

  @Test
  void updateTeam_shouldReturnUpdatedTeam_whenFound() {
    when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
    when(teamRepository.save(any(Team.class))).thenReturn(testTeam);
    Team updatedTeam = teamService.updateTeam(1L, "Updated Team Name");
    assertNotNull(updatedTeam);
    assertEquals("Updated Team Name", updatedTeam.getName());
  }

  @Test
  void updateTeam_shouldThrowException_whenNotFound() {
    when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> teamService.updateTeam(2L, "New Name"));
  }

  @Test
  void deleteTeam_shouldCallDeleteById() {
    doNothing().when(teamRepository).deleteById(anyLong());
    teamService.deleteTeam(1L);
    verify(teamRepository, times(1)).deleteById(1L);
  }

  @Test
  void addWatcherToTeam_shouldAddWatcher() {
    when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
    when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
    when(teamWatcherRepository.existsById_TeamIdAndId_UserId(testTeam.getId(), testUser.getId()))
        .thenReturn(false);

    teamService.addWatcherToTeam(1L, testUser.getUsername());

    verify(teamWatcherRepository, times(1)).save(any(TeamWatcher.class));
  }

  @Test
  void addWatcherToTeam_shouldThrowException_whenTeamNotFound() {
    when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class,
        () -> teamService.addWatcherToTeam(1L, "anyUser"));
  }

  @Test
  void addWatcherToTeam_shouldThrowException_whenUserNotFound() {
    when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
    when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    assertThrows(InvalidRequestException.class,
        () -> teamService.addWatcherToTeam(1L, "nonExistentUser"));
  }

  @Test
  void addWatcherToTeam_shouldThrowException_whenUserAlreadyWatcher() {
    when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
    when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
    when(teamWatcherRepository.existsById_TeamIdAndId_UserId(testTeam.getId(), testUser.getId()))
        .thenReturn(true);
    assertThrows(ConflictException.class,
        () -> teamService.addWatcherToTeam(1L, testUser.getUsername()));
  }

  @Test
  void removeWatcherFromTeam_shouldRemoveWatcher() {
    when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
    when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
    doNothing().when(teamWatcherRepository).deleteById_TeamIdAndId_UserId(testTeam.getId(),
        testUser.getId());

    teamService.removeWatcherFromTeam(1L, testUser.getUsername());

    verify(teamWatcherRepository, times(1)).deleteById_TeamIdAndId_UserId(testTeam.getId(),
        testUser.getId());
  }

  @Test
  void removeWatcherFromTeam_shouldThrowException_whenTeamNotFound() {
    when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class,
        () -> teamService.removeWatcherFromTeam(1L, "anyUser"));
  }

  @Test
  void removeWatcherFromTeam_shouldThrowException_whenUserNotFound() {
    when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
    when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    assertThrows(InvalidRequestException.class,
        () -> teamService.removeWatcherFromTeam(1L, "nonExistentUser"));
  }

  @Test
  void getTeamsByWatcherName_shouldReturnListOfTeams() {
    String username = "testuser";
    List<Team> teams = Arrays.asList(testTeam, Team.of("Another Team"));
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
    when(teamRepository.findAllByWatcherId(testUser.getId())).thenReturn(teams);

    List<Team> foundTeams = teamService.getTeamsByWatcherName(username);

    assertNotNull(foundTeams);
    assertEquals(2, foundTeams.size());
    assertEquals("Test Team", foundTeams.get(0).getName());
    assertEquals("Another Team", foundTeams.get(1).getName());
    verify(userRepository, times(1)).findByUsername(username);
    verify(teamRepository, times(1)).findAllByWatcherId(testUser.getId());
  }
}
