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
import jp.ponkichi.bbgreen.entity.Player;
import jp.ponkichi.bbgreen.entity.User;
import jp.ponkichi.bbgreen.entity.intermediate.PlayerWatcher;
import jp.ponkichi.bbgreen.exception.ConflictException;
import jp.ponkichi.bbgreen.exception.InvalidRequestException;
import jp.ponkichi.bbgreen.exception.ResourceNotFoundException;
import jp.ponkichi.bbgreen.repository.PlayerRepository;
import jp.ponkichi.bbgreen.repository.PlayerWatcherRepository;
import jp.ponkichi.bbgreen.repository.UserRepository;


@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

  @Mock
  private PlayerRepository playerRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private PlayerWatcherRepository playerWatcherRepository;

  @InjectMocks
  private PlayerService playerService;

  private Player testPlayer;
  private User testUser;
  private PlayerWatcher testPlayerWatcher;

  @BeforeEach
  void setUp() {
    testPlayer = Player.of("Test Player");
    setId(testPlayer, 1L);

    testUser = User.of("testuser", new Password.Encoded("password"));
    setId(testUser, 1L);
    testPlayerWatcher = PlayerWatcher.of(testPlayer.getId(), testUser.getId());
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
  void createPlayer_shouldReturnSavedPlayer() {
    String playerName = "Test Player";
    String username = "testuser";

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
    when(playerRepository.save(any(Player.class))).thenReturn(testPlayer);
    when(playerWatcherRepository.save(any(PlayerWatcher.class))).thenReturn(testPlayerWatcher);

    Player createdPlayer = playerService.createPlayer(playerName, username);

    assertNotNull(createdPlayer);
    assertEquals(playerName, createdPlayer.getName());
    verify(userRepository, times(1)).findByUsername(username);
    verify(playerRepository, times(1)).save(any(Player.class));
    verify(playerWatcherRepository, times(1)).save(any(PlayerWatcher.class));
  }

  @Test
  void createPlayer_shouldThrowException_whenUserNotFound() {
    String playerName = "Test Player";
    String username = "nonExistentUser";

    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(InvalidRequestException.class,
        () -> playerService.createPlayer(playerName, username));
    verify(userRepository, times(1)).findByUsername(username);
    verify(playerRepository, times(0)).save(any(Player.class));
    verify(playerWatcherRepository, times(0)).save(any(PlayerWatcher.class));
  }

  @Test
  void getPlayerById_shouldReturnPlayer_whenFound() {
    when(playerRepository.findById(1L)).thenReturn(Optional.of(testPlayer));
    Player foundPlayer = playerService.getPlayerById(1L);
    assertNotNull(foundPlayer);
    assertEquals("Test Player", foundPlayer.getName());
  }

  @Test
  void getPlayerById_shouldThrowException_whenNotFound() {
    when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> playerService.getPlayerById(2L));
  }

  @Test
  void getAllPlayers_shouldReturnListOfPlayers() {
    List<Player> players = Arrays.asList(testPlayer, Player.of("Another Player"));
    when(playerRepository.findAll()).thenReturn(players);
    List<Player> foundPlayers = playerService.getAllPlayers();
    assertNotNull(foundPlayers);
    assertEquals(2, foundPlayers.size());
  }

  @Test
  void getPlayersByWatcherName_shouldReturnListOfPlayers() {
    String username = "testuser";
    List<Player> players = Arrays.asList(testPlayer, Player.of("Another Player"));
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
    when(playerRepository.findAllByWatcherId(testUser.getId())).thenReturn(players);

    List<Player> foundPlayers = playerService.getPlayersByWatcherName(username);

    assertNotNull(foundPlayers);
    assertEquals(2, foundPlayers.size());
    assertEquals("Test Player", foundPlayers.get(0).getName());
    assertEquals("Another Player", foundPlayers.get(1).getName());
    verify(userRepository, times(1)).findByUsername(username);
    verify(playerRepository, times(1)).findAllByWatcherId(testUser.getId());
  }

  @Test
  void getPlayersByWatcherName_shouldThrowException_whenUserNotFound() {
    String username = "nonExistentUser";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(InvalidRequestException.class,
        () -> playerService.getPlayersByWatcherName(username));
    verify(userRepository, times(1)).findByUsername(username);
    verify(playerRepository, times(0)).findAllByWatcherId(anyLong());
  }

  @Test
  void updatePlayer_shouldReturnUpdatedPlayer_whenFound() {
    when(playerRepository.findById(1L)).thenReturn(Optional.of(testPlayer));
    when(playerRepository.save(any(Player.class))).thenReturn(testPlayer);
    Player updatedPlayer = playerService.updatePlayer(1L, "Updated Player Name");
    assertNotNull(updatedPlayer);
    assertEquals("Updated Player Name", updatedPlayer.getName());
  }

  @Test
  void updatePlayer_shouldThrowException_whenNotFound() {
    when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> playerService.updatePlayer(2L, "New Name"));
  }

  @Test
  void deletePlayer_shouldCallDelete() {
    when(playerRepository.findById(1L)).thenReturn(Optional.of(testPlayer));
    doNothing().when(playerRepository).delete(any(Player.class));

    playerService.deletePlayer(1L);

    verify(playerRepository, times(1)).findById(1L);
    verify(playerRepository, times(1)).delete(testPlayer);
  }

  @Test
  void deletePlayer_shouldThrowException_whenNotFound() {
    when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> playerService.deletePlayer(2L));
  }

  @Test
  void addWatcherToPlayer_shouldAddWatcher() {
    when(playerRepository.findById(1L)).thenReturn(Optional.of(testPlayer));
    when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
    when(playerWatcherRepository.existsById_PlayerIdAndId_UserId(testPlayer.getId(),
        testUser.getId())).thenReturn(false);
    when(playerWatcherRepository.save(any(PlayerWatcher.class))).thenReturn(testPlayerWatcher);

    playerService.addWatcherToPlayer(1L, testUser.getUsername());

    verify(playerWatcherRepository, times(1)).save(any(PlayerWatcher.class));
    verify(playerRepository, times(1)).findById(1L);
    verify(userRepository, times(1)).findByUsername(testUser.getUsername());
    verify(playerWatcherRepository, times(1)).existsById_PlayerIdAndId_UserId(testPlayer.getId(),
        testUser.getId());
  }

  @Test
  void addWatcherToPlayer_shouldThrowException_whenPlayerNotFound() {
    when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class,
        () -> playerService.addWatcherToPlayer(1L, "anyUser"));
  }

  @Test
  void addWatcherToPlayer_shouldThrowException_whenUserNotFound() {
    when(playerRepository.findById(1L)).thenReturn(Optional.of(testPlayer));
    when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    assertThrows(InvalidRequestException.class,
        () -> playerService.addWatcherToPlayer(1L, "nonExistentUser"));
  }

  @Test
  void addWatcherToPlayer_shouldThrowException_whenUserAlreadyWatcher() {
    when(playerRepository.findById(1L)).thenReturn(Optional.of(testPlayer));
    when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
    when(playerWatcherRepository.existsById_PlayerIdAndId_UserId(testPlayer.getId(),
        testUser.getId())).thenReturn(true);
    assertThrows(ConflictException.class,
        () -> playerService.addWatcherToPlayer(1L, testUser.getUsername()));
  }

  @Test
  void removeWatcherFromPlayer_shouldRemoveWatcher() {
    when(playerRepository.findById(1L)).thenReturn(Optional.of(testPlayer));
    when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
    doNothing().when(playerWatcherRepository).deleteById_PlayerIdAndId_UserId(any(Long.class),
        any(Long.class));

    playerService.removeWatcherFromPlayer(1L, testUser.getUsername());

    verify(playerWatcherRepository, times(1)).deleteById_PlayerIdAndId_UserId(testPlayer.getId(),
        testUser.getId());
    verify(playerRepository, times(1)).findById(1L);
    verify(userRepository, times(1)).findByUsername(testUser.getUsername());
  }

  @Test
  void removeWatcherFromPlayer_shouldThrowException_whenPlayerNotFound() {
    when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class,
        () -> playerService.removeWatcherFromPlayer(1L, "anyUser"));
  }

  @Test
  void removeWatcherFromPlayer_shouldThrowException_whenUserNotFound() {
    when(playerRepository.findById(1L)).thenReturn(Optional.of(testPlayer));
    when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    assertThrows(InvalidRequestException.class,
        () -> playerService.removeWatcherFromPlayer(1L, "nonExistentUser"));
  }
}
