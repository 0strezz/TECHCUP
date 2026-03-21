package main.java.edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.dto.CreateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UpdateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UserResponseDto;
import main.java.edu.eci.dosw.tech_cup.dto.ChangeUserRoleRequestDto;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final List<UserResponseDto> users;

    public UserService() {
        this.users = new ArrayList<>();

        users.add(new UserResponseDto(
                UUID.randomUUID(),
                "Admin User",
                "admin@techcup.com",
                "ADMINISTRATOR",
                true,
                "Dummy admin user"
        ));

        users.add(new UserResponseDto(
                UUID.randomUUID(),
                "Dummy Player",
                "player@techcup.com",
                "PLAYER",
                true,
                "Dummy player user"
        ));
    }

    public UserResponseDto createUser(CreateUserRequestDto request) {
        UserResponseDto newUser = new UserResponseDto(
            UUID.randomUUID(),
            request.getName(),
            request.getEmail(),
            "PLAYER", //default role
            true,
            "User created successfully"
        );

        users.add(newUser);
        return newUser;
    }

    public List<UserResponseDto> getAllUsers() {
        return users;
    }

    public UserResponseDto getUserById(UUID id) {
        for (UserResponseDto user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public UserResponseDto updateUser(UUID id, UpdateUserRequestDto request) {
        for (UserResponseDto user : users) {
            if (user.getId().equals(id)) {
                user.setName(request.getName());
                user.setEmail(request.getEmail());
                user.setMessage("User updated successfully");
                return user;
            }
        }
        return null;
    }

    public UserResponseDto inactivateUser(UUID id) {
        for (UserResponseDto user : users) {
            if (user.getId().equals(id)) {
                user.setActive(false);
                user.setMessage("User inactivated successfully");
                return user;
            }
        }
        return null;
    }

    public UserResponseDto changeUserRole(UUID id, ChangeUserRoleRequestDto request) {
        UserResponseDto actingUser = null;

        for (UserResponseDto user : users) {
            if (user.getEmail().equals(request.getPerformedBy())) {
                actingUser = user;
                break;
            }
        }

        if (actingUser == null) {
            return new UserResponseDto(
                null,
                null,
                null,
                null,
                false,
                "The user performing the action does not exist"
            );
        }

        if (!"ADMINISTRATOR".equals(actingUser.getRole())) {
            return new UserResponseDto(
                null,
                null,
                null,
                null,
                false,
                "Only an administrator can assign a different role"
            );
        }

        UserResponseDto targetUser = getUserById(id);
        if (targetUser == null) {
            return new UserResponseDto(
                null,
                null,
                null,
                null,
                false,
                "Target user not found"
            );
        }
        targetUser.setRole(request.getNewRole());
        targetUser.setMessage("User role updated successfully");
        return targetUser;
    }

}