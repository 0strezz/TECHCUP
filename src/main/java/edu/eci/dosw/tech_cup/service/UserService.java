package edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.dto.ChangeUserRoleRequestDto;
import edu.eci.dosw.tech_cup.dto.CreateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UpdateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UserResponseDto;
import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import edu.eci.dosw.tech_cup.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto createUser(CreateUserRequestDto request) {
        log.info("Creating user with email={}", request.getEmail());

        UserEntity entity = new UserEntity(
                request.getName(),
                request.getEmail(),
            passwordEncoder.encode(request.getPassword()),
                "PLAYER",
                true,
                LocalDateTime.now()
        );

        UserEntity saved = userRepository.save(entity);
        log.debug("User created with id={}", saved.getId());
        return toDto(saved, "User created successfully");
    }

    public List<UserResponseDto> getAllUsers() {
        log.debug("Fetching all users");
        return userRepository.findAll()
                .stream()
                .map(u -> toDto(u, null))
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(UUID id) {
        log.debug("Searching user by id={}", id);
        Optional<UserEntity> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            log.debug("User not found for id={}", id);
            return null;
        }
        return toDto(opt.get(), null);
    }

    public UserResponseDto updateUser(UUID id, UpdateUserRequestDto request) {
        log.info("Updating user id={}", id);
        Optional<UserEntity> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            log.debug("Update failed. user not found id={}", id);
            return null;
        }

        UserEntity entity = opt.get();
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        userRepository.save(entity);
        log.debug("User updated id={}", id);
        return toDto(entity, "User updated successfully");
    }

    public UserResponseDto inactivateUser(UUID id) {
        log.info("Inactivating user id={}", id);
        Optional<UserEntity> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            log.debug("Inactivation failed. user not found id={}", id);
            return null;
        }

        UserEntity entity = opt.get();
        entity.setActive(false);
        userRepository.save(entity);
        log.debug("User inactivated id={}", id);
        return toDto(entity, "User inactivated successfully");
    }

    public UserResponseDto changeUserRole(UUID id, ChangeUserRoleRequestDto request) {
        log.info("Changing role for targetUserId={} by actorEmail={}", id, request.getPerformedBy());

        Optional<UserEntity> actorOpt = userRepository.findByEmail(request.getPerformedBy());
        if (actorOpt.isEmpty()) {
            log.debug("Role change denied. acting user does not exist for email={}", request.getPerformedBy());
            return errorUser("The user performing the action does not exist");
        }

        UserEntity actingUser = actorOpt.get();
        if (!"ADMINISTRATOR".equals(actingUser.getRole())) {
            log.debug("Role change denied. actor role is {}", actingUser.getRole());
            return errorUser("Only an administrator can assign a different role");
        }

        Optional<UserEntity> targetOpt = userRepository.findById(id);
        if (targetOpt.isEmpty()) {
            log.debug("Role change failed. target user not found id={}", id);
            return errorUser("Target user not found");
        }

        UserEntity target = targetOpt.get();
        target.setRole(request.getNewRole());
        userRepository.save(target);
        log.info("Role changed successfully for user id={} to role={}", id, request.getNewRole());
        return toDto(target, "User role updated successfully");
    }

    // --- Helpers ---

    private UserResponseDto toDto(UserEntity entity, String message) {
        return new UserResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRole(),
                entity.isActive(),
                message
        );
    }

    private UserResponseDto errorUser(String message) {
        return new UserResponseDto(null, null, null, null, false, message);
    }
}