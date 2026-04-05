package jp.ponkichi.bbgreen.service;

import jp.ponkichi.bbgreen.dto.converter.PasswordEncodedConverter;
import jp.ponkichi.bbgreen.entity.Team;
import jp.ponkichi.bbgreen.entity.TeamUser;
import jp.ponkichi.bbgreen.entity.User;
import jp.ponkichi.bbgreen.repository.TeamRepository;
import jp.ponkichi.bbgreen.repository.TeamUserRepository;
import jp.ponkichi.bbgreen.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TeamUserRepository teamUserRepository;

    @InjectMocks
    private TeamService teamService;

    private Team testTeam;
    private User testUser;

    @BeforeEach
    void setUp() {
        testTeam = Team.of("Test Team");

        testUser = User.of("testuser", "password", "test@example.com");

        // Mock the getId() method for testTeam and testUser
        // This is necessary because getId() is called within the service methods
        // and Team and User are not mocks themselves.
        // If Team and User were mocks, you would mock their behavior directly.
        try {
            java.lang.reflect.Field idFieldTeam = Team.class.getDeclaredField("id");
            idFieldTeam.setAccessible(true);
            idFieldTeam.set(testTeam, 1L);

            java.lang.reflect.Field idFieldUser = User.class.getDeclaredField("id");
            idFieldUser.setAccessible(true);
            idFieldUser.set(testUser, 1L);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createTeam_shouldReturnSavedTeam() {
        String teamName = "Test Team";
        String username = "testuser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
        when(teamRepository.save(any(Team.class))).thenReturn(testTeam);
        when(teamUserRepository.save(any(TeamUser.class))).thenReturn(any(TeamUser.class)); // Can mock this as well if
                                                                                            // needed

        Team createdTeam = teamService.createTeam(teamName, username);

        assertNotNull(createdTeam);
        assertEquals(teamName, createdTeam.getName());
        verify(userRepository, times(1)).findByUsername(username);
        verify(teamRepository, times(1)).save(any(Team.class));
        verify(teamUserRepository, times(1)).save(any(TeamUser.class));
    }

    @Test
    void getTeamById_shouldReturnTeam_whenFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
        Team foundTeam = teamService.getTeamById(1L);
        assertNotNull(foundTeam);
        assertEquals("Test Team", foundTeam.getName());
    }

    @Test
    void getTeamById_shouldReturnNull_whenNotFound() {
        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());
        Team foundTeam = teamService.getTeamById(2L);
        assertNull(foundTeam);
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
        assertThrows(IllegalArgumentException.class, () -> teamService.updateTeam(2L, "New Name"));
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
        when(teamUserRepository.existsByTeamAndUser(testTeam, testUser)).thenReturn(false);

        teamService.addWatcherToTeam(1L, 1L);

        verify(teamUserRepository, times(1)).save(any(TeamUser.class));
    }

    @Test
    void addWatcherToTeam_shouldThrowException_whenTeamNotFound() {
        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> teamService.addWatcherToTeam(1L, 1L));
    }

    @Test
    void addWatcherToTeam_shouldThrowException_whenUserNotFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> teamService.addWatcherToTeam(1L, 1L));
    }

    @Test
    void addWatcherToTeam_shouldThrowException_whenUserAlreadyWatcher() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(teamUserRepository.existsByTeamAndUser(testTeam, testUser)).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> teamService.addWatcherToTeam(1L, 1L));
    }

    @Test
    void removeWatcherFromTeam_shouldRemoveWatcher() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        doNothing().when(teamUserRepository).deleteByTeamAndUser(any(Team.class), any(User.class));

        teamService.removeWatcherFromTeam(1L, 1L);

        verify(teamUserRepository, times(1)).deleteByTeamAndUser(testTeam, testUser);
    }

    @Test
    void removeWatcherFromTeam_shouldThrowException_whenTeamNotFound() {
        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> teamService.removeWatcherFromTeam(1L, 1L));
    }

    @Test
    void removeWatcherFromTeam_shouldThrowException_whenUserNotFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> teamService.removeWatcherFromTeam(1L, 1L));
    }

    @Test
    void getTeamWatchers_shouldReturnWatcherIds() {
        Long[] userIds = { 1L, 2L };
        when(teamUserRepository.findUserIdsByTeamId(1L)).thenReturn(userIds);
        Long[] result = teamService.getTeamWatchers(1L);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertArrayEquals(userIds, result);
    }
}
