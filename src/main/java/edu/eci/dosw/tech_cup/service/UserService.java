package edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.dto.CreateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UpdateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UserResponseDto;
import edu.eci.dosw.tech_cup.dto.ChangeUserRoleRequestDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final List<UserResponseDto> users;

    public UserService() {
        this.users = new ArrayList<>();
        log.info("Initializing UserService with dummy users");

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
        log.info("Creating user with email={}", request.getEmail());
        UserResponseDto newUser = new UserResponseDto(
            UUID.randomUUID(),
            request.getName(),
            request.getEmail(),
            "PLAYER", //default role
            true,
            "User created successfully"
        );

        users.add(newUser);
        log.debug("User created with id={}", newUser.getId());
        return newUser;
    }

    public List<UserResponseDto> getAllUsers() {
        log.debug("Fetching all users. count={}", users.size());
        return users;
    }

    public UserResponseDto getUserById(UUID id) {
        log.debug("Searching user by id={}", id);
        for (UserResponseDto user : users) {
            if (user.getId().equals(id)) {
                log.debug("User found for id={}", id);
                return user;
            }
        }
        log.debug("User not found for id={}", id);
        return null;
    }

    public UserResponseDto updateUser(UUID id, UpdateUserRequestDto request) {
        log.info("Updating user id={}", id);
        for (UserResponseDto user : users) {
            if (user.getId().equals(id)) {
                user.setName(request.getName());
                user.setEmail(request.getEmail());
                user.setMessage("User updated successfully");
                log.debug("User updated id={}", id);
                return user;
            }
        }
        log.debug("Update failed. user not found id={}", id);
        return null;
    }

    public UserResponseDto inactivateUser(UUID id) {
        log.info("Inactivating user id={}", id);
        for (UserResponseDto user : users) {
            if (user.getId().equals(id)) {
                user.setActive(false);
                user.setMessage("User inactivated successfully");
                log.debug("User inactivated id={}", id);
                return user;
            }
        }
        log.debug("Inactivation failed. user not found id={}", id);
        return null;
    }

    public UserResponseDto changeUserRole(UUID id, ChangeUserRoleRequestDto request) {
        log.info("Changing role for targetUserId={} by actorEmail={}", id, request.getPerformedBy());
        UserResponseDto actingUser = null;

        for (UserResponseDto user : users) {
            if (user.getEmail().equals(request.getPerformedBy())) {
                actingUser = user;
                break;
            }
        }

        if (actingUser == null) {
            log.debug("Role change denied. acting user does not exist for email={}", request.getPerformedBy());
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
            log.debug("Role change denied. actor role is {}", actingUser.getRole());
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
            log.debug("Role change failed. target user not found id={}", id);
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
        log.info("Role changed successfully for user id={} to role={}", id, request.getNewRole());
        return targetUser;
    }

}