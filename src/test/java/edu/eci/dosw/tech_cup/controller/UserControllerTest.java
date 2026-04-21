package edu.eci.dosw.tech_cup.controller;

import edu.eci.dosw.tech_cup.dto.ChangeUserRoleRequestDto;
import edu.eci.dosw.tech_cup.dto.CreateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UpdateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UserResponseDto;
import edu.eci.dosw.tech_cup.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserController tests")
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser_Success() {
        CreateUserRequestDto req = new CreateUserRequestDto("User", "u@u.com", "pass");
        UserResponseDto res = new UserResponseDto();
        res.setMessage("User created successfully");
        when(userService.createUser(req)).thenReturn(res);

        ResponseEntity<UserResponseDto> response = userController.createUser(req);
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void createUser_Failure() {
        CreateUserRequestDto req = new CreateUserRequestDto("User", "u@u.com", "pass");
        UserResponseDto res = new UserResponseDto();
        res.setMessage("Email already exists");
        when(userService.createUser(req)).thenReturn(res);

        ResponseEntity<UserResponseDto> response = userController.createUser(req);
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void getAllUsers() {
        when(userService.getAllUsers()).thenReturn(List.of(new UserResponseDto()));
        ResponseEntity<List<UserResponseDto>> response = userController.getAllUsers();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getUserById_Found() {
        UUID id = UUID.randomUUID();
        when(userService.getUserById(id)).thenReturn(new UserResponseDto());
        ResponseEntity<UserResponseDto> response = userController.getUserById(id);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getUserById_NotFound() {
        UUID id = UUID.randomUUID();
        when(userService.getUserById(id)).thenReturn(null);
        ResponseEntity<UserResponseDto> response = userController.getUserById(id);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void updateUser_Found() {
        UUID id = UUID.randomUUID();
        UpdateUserRequestDto req = new UpdateUserRequestDto();
        UserResponseDto res = new UserResponseDto();
        when(userService.updateUser(id, req)).thenReturn(res);

        ResponseEntity<UserResponseDto> response = userController.updateUser(id, req);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void updateUser_NotFound() {
        UUID id = UUID.randomUUID();
        when(userService.updateUser(id, null)).thenReturn(null);
        ResponseEntity<UserResponseDto> response = userController.updateUser(id, null);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void inactivateUser_Found() {
        UUID id = UUID.randomUUID();
        UserResponseDto res = new UserResponseDto();
        when(userService.inactivateUser(id)).thenReturn(res);

        ResponseEntity<UserResponseDto> response = userController.inactivateUser(id);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void inactivateUser_NotFound() {
        UUID id = UUID.randomUUID();
        when(userService.inactivateUser(id)).thenReturn(null);
        ResponseEntity<UserResponseDto> response = userController.inactivateUser(id);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void changeUserRole_Success() {
        UUID id = UUID.randomUUID();
        ChangeUserRoleRequestDto req = new ChangeUserRoleRequestDto();
        UserResponseDto res = new UserResponseDto();
        res.setMessage("User role updated successfully");
        when(userService.changeUserRole(id, req)).thenReturn(res);

        ResponseEntity<UserResponseDto> response = userController.changeUserRole(id, req);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void changeUserRole_Failure() {
        UUID id = UUID.randomUUID();
        ChangeUserRoleRequestDto req = new ChangeUserRoleRequestDto();
        UserResponseDto res = new UserResponseDto();
        res.setMessage("Some error");
        when(userService.changeUserRole(id, req)).thenReturn(res);

        ResponseEntity<UserResponseDto> response = userController.changeUserRole(id, req);
        assertEquals(200, response.getStatusCode().value());
    }
}
