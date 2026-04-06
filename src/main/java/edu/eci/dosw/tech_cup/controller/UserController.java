package edu.eci.dosw.tech_cup.controller;

import edu.eci.dosw.tech_cup.dto.ChangeUserRoleRequestDto;
import edu.eci.dosw.tech_cup.dto.CreateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UpdateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UserResponseDto;
import edu.eci.dosw.tech_cup.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto request) {
        UserResponseDto response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        UserResponseDto user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequestDto request) {

        UserResponseDto updatedUser = userService.updateUser(id, request);

        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}/inactive")
    public ResponseEntity<UserResponseDto> inactivateUser(@PathVariable UUID id) {
        UserResponseDto user = userService.inactivateUser(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserResponseDto> changeUserRole(
            @PathVariable UUID id,
            @Valid @RequestBody ChangeUserRoleRequestDto request) {

        UserResponseDto user = userService.changeUserRole(id, request);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if ("The user performing the action does not exist".equals(user.getMessage())
                || "Target user not found".equals(user.getMessage())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
        }

        if ("Only an administrator can assign a different role".equals(user.getMessage())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(user);
        }

        return ResponseEntity.ok(user);
    }
}