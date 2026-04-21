package edu.eci.dosw.tech_cup.dto;

import edu.eci.dosw.tech_cup.model.enums.TournamentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DTO tests")
class DtoTest {

    @Test
    @DisplayName("LoginRequestDto getters/setters/constructors")
    void loginRequestDto() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("e@e.com");
        dto.setPassword("pass");
        
        assertEquals("e@e.com", dto.getEmail());
        assertEquals("pass", dto.getPassword());

        LoginRequestDto dto2 = new LoginRequestDto("e@e.com", "pass");
        assertEquals("e@e.com", dto2.getEmail());
    }

    @Test
    @DisplayName("LoginResponseDto getters/setters/constructors")
    void loginResponseDto() {
        LoginResponseDto dto = new LoginResponseDto();
        UUID id = UUID.randomUUID();
        dto.setId(id);
        dto.setEmail("e@e.com");
        dto.setName("Name");
        dto.setRoles(List.of("ADMIN"));
        dto.setAccessToken("access");
        dto.setRefreshToken("refresh");
        dto.setTokenType("Bearer");
        dto.setExpiresIn(3600L);
        dto.setMessage("msg");

        assertEquals(id, dto.getId());
        assertEquals("e@e.com", dto.getEmail());
        assertEquals("Name", dto.getName());
        assertEquals(1, dto.getRoles().size());
        assertEquals("access", dto.getAccessToken());
        assertEquals("refresh", dto.getRefreshToken());
        assertEquals("Bearer", dto.getTokenType());
        assertEquals(3600L, dto.getExpiresIn());
        assertEquals("msg", dto.getMessage());

        LoginResponseDto dto2 = new LoginResponseDto(id, "Name", "e@e.com", List.of("ADMIN"), "msg");
        assertEquals(id, dto2.getId());

        LoginResponseDto dto3 = new LoginResponseDto(id, "Name", "e@e.com", List.of("ADMIN"), "acc", "ref", "Bear", 3600L, "msg");
        assertEquals("acc", dto3.getAccessToken());
    }

    @Test
    @DisplayName("RefreshTokenRequestDto getters/setters/constructors")
    void refreshTokenRequestDto() {
        RefreshTokenRequestDto dto = new RefreshTokenRequestDto();
        dto.setRefreshToken("refresh");
        assertEquals("refresh", dto.getRefreshToken());
        
        RefreshTokenRequestDto dto2 = new RefreshTokenRequestDto("refresh");
        assertEquals("refresh", dto2.getRefreshToken());
    }

    @Test
    @DisplayName("ChangeUserRoleRequestDto getters/setters/constructors")
    void changeUserRoleRequestDto() {
        ChangeUserRoleRequestDto dto = new ChangeUserRoleRequestDto();
        dto.setNewRole("ADMIN");
        dto.setPerformedBy("admin@test.com");
        assertEquals("ADMIN", dto.getNewRole());
        assertEquals("admin@test.com", dto.getPerformedBy());

        ChangeUserRoleRequestDto dto2 = new ChangeUserRoleRequestDto("ADMIN", "admin@test.com");
        assertEquals("ADMIN", dto2.getNewRole());
    }

    @Test
    @DisplayName("CreateTournamentRequestDto getters/setters/constructors")
    void createTournamentRequestDto() {
        CreateTournamentRequestDto dto = new CreateTournamentRequestDto();
        LocalDate now = LocalDate.now();
        dto.setName("Name");
        dto.setStartDate(now);
        dto.setEndDate(now.plusDays(1));
        dto.setNumberOfTeams(8);

        assertEquals("Name", dto.getName());
        assertEquals(now, dto.getStartDate());
        assertEquals(now.plusDays(1), dto.getEndDate());
        assertEquals(8, dto.getNumberOfTeams());

        CreateTournamentRequestDto dto2 = new CreateTournamentRequestDto("Name", now, now, 8);
        assertEquals("Name", dto2.getName());
    }

    @Test
    @DisplayName("UpdateTournamentRequestDto getters/setters/constructors")
    void updateTournamentRequestDto() {
        UpdateTournamentRequestDto dto = new UpdateTournamentRequestDto();
        LocalDate now = LocalDate.now();
        dto.setName("Name");
        dto.setStartDate(now);
        dto.setEndDate(now.plusDays(1));
        dto.setNumberOfTeams(8);

        assertEquals("Name", dto.getName());
        assertEquals(now, dto.getStartDate());
        assertEquals(now.plusDays(1), dto.getEndDate());
        assertEquals(8, dto.getNumberOfTeams());

        UpdateTournamentRequestDto dto2 = new UpdateTournamentRequestDto("Name", now, now, 8);
        assertEquals("Name", dto2.getName());
    }

    @Test
    @DisplayName("TournamentResponseDto getters/setters/constructors")
    void tournamentResponseDto() {
        TournamentResponseDto dto = new TournamentResponseDto();
        UUID id = UUID.randomUUID();
        LocalDate now = LocalDate.now();
        
        dto.setId(id);
        dto.setName("Name");
        dto.setStartDate(now);
        dto.setEndDate(now);
        dto.setNumberOfTeams(8);
        dto.setStatus(TournamentStatus.ACTIVE);
        dto.setMessage("msg");

        assertEquals(id, dto.getId());
        assertEquals("Name", dto.getName());
        assertEquals(now, dto.getStartDate());
        assertEquals(now, dto.getEndDate());
        assertEquals(8, dto.getNumberOfTeams());
        assertEquals(TournamentStatus.ACTIVE, dto.getStatus());
        assertEquals("msg", dto.getMessage());

        TournamentResponseDto dto2 = new TournamentResponseDto(id, "n", now, now, 8, TournamentStatus.ACTIVE, "msg");
        assertEquals("n", dto2.getName());
    }

    @Test
    @DisplayName("CreateUserRequestDto getters/setters/constructors")
    void createUserRequestDto() {
        CreateUserRequestDto dto = new CreateUserRequestDto();
        dto.setName("Name");
        dto.setEmail("e@e.com");
        dto.setPassword("pass");

        assertEquals("Name", dto.getName());
        assertEquals("e@e.com", dto.getEmail());
        assertEquals("pass", dto.getPassword());

        CreateUserRequestDto dto2 = new CreateUserRequestDto("Name", "e@e.com", "pass");
        assertEquals("Name", dto2.getName());
    }

    @Test
    @DisplayName("UpdateUserRequestDto getters/setters/constructors")
    void updateUserRequestDto() {
        UpdateUserRequestDto dto = new UpdateUserRequestDto();
        dto.setName("Name");
        dto.setEmail("e@e.com");

        assertEquals("Name", dto.getName());
        assertEquals("e@e.com", dto.getEmail());

        UpdateUserRequestDto dto2 = new UpdateUserRequestDto("Name", "e@e.com");
        assertEquals("Name", dto2.getName());
    }

    @Test
    @DisplayName("UserResponseDto getters/setters/constructors")
    void userResponseDto() {
        UserResponseDto dto = new UserResponseDto();
        UUID id = UUID.randomUUID();
        
        dto.setId(id);
        dto.setName("Name");
        dto.setEmail("e@e.com");
        dto.setRoles(List.of("ADMIN"));
        dto.setActive(true);
        dto.setMessage("msg");

        assertEquals(id, dto.getId());
        assertEquals("Name", dto.getName());
        assertEquals("e@e.com", dto.getEmail());
        assertEquals(1, dto.getRoles().size());
        assertTrue(dto.getActive());
        assertEquals("msg", dto.getMessage());

        UserResponseDto dto2 = new UserResponseDto(id, "Name", "e@e.com", List.of("ADMIN"), true, "msg");
        assertEquals(id, dto2.getId());
    }
}
