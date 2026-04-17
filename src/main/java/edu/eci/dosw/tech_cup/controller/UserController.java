package edu.eci.dosw.tech_cup.controller;

import edu.eci.dosw.tech_cup.dto.ChangeUserRoleRequestDto;
import edu.eci.dosw.tech_cup.dto.CreateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UpdateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UserResponseDto;
import edu.eci.dosw.tech_cup.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Operations related to user management")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Register user", description = "Creates a new user account in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto request) {
        UserResponseDto response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Returns all registered users.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by id",
            description = "Returns a user by its identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        UserResponseDto user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update user",
            description = "Updates a user.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
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
    @Operation(
            summary = "Inactivate user",
            description = "Marks a user as inactive.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<UserResponseDto> inactivateUser(@PathVariable UUID id) {
        UserResponseDto user = userService.inactivateUser(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/role")
    @Operation(
            summary = "Change user role",
            description = "Changes the role of a user. Only administrators are allowed.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
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