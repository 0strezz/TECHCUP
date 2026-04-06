package edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.dto.ChangeUserRoleRequestDto;
import edu.eci.dosw.tech_cup.dto.CreateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UpdateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UserResponseDto;
import edu.eci.dosw.tech_cup.entity.user.RoleEntity;
import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import edu.eci.dosw.tech_cup.repository.RoleRepository;
import edu.eci.dosw.tech_cup.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto createUser(CreateUserRequestDto request) {
        log.info("Creating user with email={}", request.getEmail());

        Optional<RoleEntity> playerRoleOpt = roleRepository.findByName("PLAYER");
        Optional<RoleEntity> adminRoleOpt = roleRepository.findByName("ADMINISTRATOR");

        if (playerRoleOpt.isEmpty()) {
            return errorUser("Default role PLAYER does not exist");
        }

        UserEntity entity = new UserEntity(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                true,
                LocalDateTime.now()
        );

        if ("admin@techcup.com".equalsIgnoreCase(request.getEmail()) && adminRoleOpt.isPresent()) {
            entity.getRoles().add(adminRoleOpt.get());
        } else {
            entity.getRoles().add(playerRoleOpt.get());
        }

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
        boolean isAdministrator = actingUser.getRoles().stream()
                .anyMatch(role -> "ADMINISTRATOR".equals(role.getName()));

        if (!isAdministrator) {
            log.debug("Role change denied. actor is not administrator");
            return errorUser("Only an administrator can assign a different role");
        }

        Optional<UserEntity> targetOpt = userRepository.findById(id);
        if (targetOpt.isEmpty()) {
            log.debug("Role change failed. target user not found id={}", id);
            return errorUser("Target user not found");
        }

        Optional<RoleEntity> newRoleOpt = roleRepository.findByName(request.getNewRole());
        if (newRoleOpt.isEmpty()) {
            log.debug("Role change failed. role does not exist: {}", request.getNewRole());
            return errorUser("The requested role does not exist");
        }

        UserEntity target = targetOpt.get();
        target.getRoles().clear();
        target.getRoles().add(newRoleOpt.get());

        userRepository.save(target);
        log.info("Role changed successfully for user id={} to role={}", id, request.getNewRole());
        return toDto(target, "User role updated successfully");
    }

    public UserDetails loadUserByEmail(String email) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        UserEntity user = userOpt.get();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .flatMap(role -> role.getPermissions().stream())
                        .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                        .toList()
        );
    }

    private UserResponseDto toDto(UserEntity entity, String message) {
        List<String> roles = entity.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());

        return new UserResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                roles,
                entity.isActive(),
                message
        );
    }

    private UserResponseDto errorUser(String message) {
        return new UserResponseDto(null, null, null, null, false, message);
    }
}