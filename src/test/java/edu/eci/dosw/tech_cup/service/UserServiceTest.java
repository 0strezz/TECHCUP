package edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.dto.ChangeUserRoleRequestDto;
import edu.eci.dosw.tech_cup.dto.CreateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UpdateUserRequestDto;
import edu.eci.dosw.tech_cup.dto.UserResponseDto;
import edu.eci.dosw.tech_cup.entity.user.RoleEntity;
import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import edu.eci.dosw.tech_cup.entity.user.PermissionEntity;
import edu.eci.dosw.tech_cup.repository.RoleRepository;
import edu.eci.dosw.tech_cup.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserEntity activeUser;
    private RoleEntity adminRole;
    private RoleEntity playerRole;

    @BeforeEach
    void setUp() {
        adminRole = new RoleEntity("ADMINISTRATOR", "Desc");
        playerRole = new RoleEntity("PLAYER", "Desc");

        activeUser = new UserEntity("Test", "test@mail.com", "pass", true, LocalDateTime.now());
        activeUser.setId(UUID.randomUUID());
        activeUser.getRoles().add(playerRole);
    }

    @Test
    void createUser_PlayerSuccess() {
        CreateUserRequestDto req = new CreateUserRequestDto("Test", "t@t.com", "pass");
        when(roleRepository.findByName("PLAYER")).thenReturn(Optional.of(playerRole));
        when(roleRepository.findByName("ADMINISTRATOR")).thenReturn(Optional.of(adminRole));
        when(passwordEncoder.encode("pass")).thenReturn("encoded_pass");
        
        UserEntity saved = new UserEntity();
        saved.setId(UUID.randomUUID());
        saved.setName("Test");
        saved.getRoles().add(playerRole);
        when(userRepository.save(any(UserEntity.class))).thenReturn(saved);

        UserResponseDto res = userService.createUser(req);

        assertNotNull(res.getId());
        assertEquals("User created successfully", res.getMessage());
        verify(userRepository).save(any());
    }

    @Test
    void createUser_FallbackMissingPlayerRole() {
        CreateUserRequestDto req = new CreateUserRequestDto("Test", "t@t.com", "pass");
        when(roleRepository.findByName("PLAYER")).thenReturn(Optional.empty());
        when(roleRepository.findByName("ADMINISTRATOR")).thenReturn(Optional.of(adminRole));

        UserResponseDto res = userService.createUser(req);
        assertEquals("Default role PLAYER does not exist", res.getMessage());
    }

    @Test
    void createUser_AdminSuccess() {
        CreateUserRequestDto req = new CreateUserRequestDto("Test", "admin@techcup.com", "pass");
        when(roleRepository.findByName("PLAYER")).thenReturn(Optional.of(playerRole));
        when(roleRepository.findByName("ADMINISTRATOR")).thenReturn(Optional.of(adminRole));
        when(passwordEncoder.encode("pass")).thenReturn("encoded");

        UserEntity saved = new UserEntity();
        saved.setId(UUID.randomUUID());
        saved.getRoles().add(adminRole);
        when(userRepository.save(any(UserEntity.class))).thenReturn(saved);

        UserResponseDto res = userService.createUser(req);
        assertEquals("User created successfully", res.getMessage());
    }

    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(activeUser));
        List<UserResponseDto> list = userService.getAllUsers();
        assertEquals(1, list.size());
    }

    @Test
    void getUserById_Found() {
        when(userRepository.findById(activeUser.getId())).thenReturn(Optional.of(activeUser));
        UserResponseDto res = userService.getUserById(activeUser.getId());
        assertNotNull(res);
        assertEquals(activeUser.getId(), res.getId());
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        UserResponseDto res = userService.getUserById(UUID.randomUUID());
        assertNull(res);
    }

    @Test
    void updateUser_Found() {
        UpdateUserRequestDto req = new UpdateUserRequestDto("New Name", "n@n.com");
        when(userRepository.findById(activeUser.getId())).thenReturn(Optional.of(activeUser));
        when(userRepository.save(any())).thenReturn(activeUser);

        UserResponseDto res = userService.updateUser(activeUser.getId(), req);
        assertEquals("User updated successfully", res.getMessage());
    }

    @Test
    void updateUser_NotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        UserResponseDto res = userService.updateUser(UUID.randomUUID(), new UpdateUserRequestDto());
        assertNull(res);
    }

    @Test
    void inactivateUser_Found() {
        when(userRepository.findById(activeUser.getId())).thenReturn(Optional.of(activeUser));
        when(userRepository.save(any())).thenReturn(activeUser);

        UserResponseDto res = userService.inactivateUser(activeUser.getId());
        assertEquals("User inactivated successfully", res.getMessage());
        assertFalse(activeUser.isActive());
    }

    @Test
    void inactivateUser_NotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        UserResponseDto res = userService.inactivateUser(UUID.randomUUID());
        assertNull(res);
    }

    @Test
    void changeUserRole_ActorNotFound() {
        ChangeUserRoleRequestDto req = new ChangeUserRoleRequestDto("PLAYER", "unknown@mail.com");
        when(userRepository.findByEmail("unknown@mail.com")).thenReturn(Optional.empty());
        
        UserResponseDto res = userService.changeUserRole(UUID.randomUUID(), req);
        assertEquals("The user performing the action does not exist", res.getMessage());
    }

    @Test
    void changeUserRole_ActorNotAdmin() {
        ChangeUserRoleRequestDto req = new ChangeUserRoleRequestDto("ADMINISTRATOR", "actor@mail.com");
        UserEntity actor = new UserEntity();
        actor.getRoles().add(playerRole); // not admin
        
        when(userRepository.findByEmail("actor@mail.com")).thenReturn(Optional.of(actor));
        
        UserResponseDto res = userService.changeUserRole(UUID.randomUUID(), req);
        assertEquals("Only an administrator can assign a different role", res.getMessage());
    }

    @Test
    void changeUserRole_TargetNotFound() {
        ChangeUserRoleRequestDto req = new ChangeUserRoleRequestDto("ADMINISTRATOR", "actor@mail.com");
        UserEntity actor = new UserEntity();
        actor.getRoles().add(adminRole);
        
        when(userRepository.findByEmail("actor@mail.com")).thenReturn(Optional.of(actor));
        when(userRepository.findById(activeUser.getId())).thenReturn(Optional.empty());

        UserResponseDto res = userService.changeUserRole(activeUser.getId(), req);
        assertEquals("Target user not found", res.getMessage());
    }

    @Test
    void changeUserRole_RoleNotFound() {
        ChangeUserRoleRequestDto req = new ChangeUserRoleRequestDto("FAKEROLE", "actor@mail.com");
        UserEntity actor = new UserEntity();
        actor.getRoles().add(adminRole);
        
        when(userRepository.findByEmail("actor@mail.com")).thenReturn(Optional.of(actor));
        when(userRepository.findById(activeUser.getId())).thenReturn(Optional.of(activeUser));
        when(roleRepository.findByName("FAKEROLE")).thenReturn(Optional.empty());

        UserResponseDto res = userService.changeUserRole(activeUser.getId(), req);
        assertEquals("The requested role does not exist", res.getMessage());
    }

    @Test
    void changeUserRole_Success() {
        ChangeUserRoleRequestDto req = new ChangeUserRoleRequestDto("ADMINISTRATOR", "actor@mail.com");
        UserEntity actor = new UserEntity();
        actor.getRoles().add(adminRole);
        
        when(userRepository.findByEmail("actor@mail.com")).thenReturn(Optional.of(actor));
        when(userRepository.findById(activeUser.getId())).thenReturn(Optional.of(activeUser));
        when(roleRepository.findByName("ADMINISTRATOR")).thenReturn(Optional.of(adminRole));
        when(userRepository.save(any())).thenReturn(activeUser);

        UserResponseDto res = userService.changeUserRole(activeUser.getId(), req);
        assertEquals("User role updated successfully", res.getMessage());
        assertEquals("ADMINISTRATOR", activeUser.getRoles().get(0).getName());
    }

    @Test
    void loadUserByEmail_NotFound() {
        when(userRepository.findByEmail("not@found.com")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.loadUserByEmail("not@found.com"));
    }

    @Test
    void loadUserByEmail_Found() {
        activeUser.getRoles().clear();
        RoleEntity r = new RoleEntity("ADMIN", "desc");
        PermissionEntity p = new PermissionEntity("READ", "desc");
        r.getPermissions().add(p);
        activeUser.getRoles().add(r);

        when(userRepository.findByEmail(activeUser.getEmail())).thenReturn(Optional.of(activeUser));
        
        UserDetails details = userService.loadUserByEmail(activeUser.getEmail());
        assertNotNull(details);
        assertEquals(activeUser.getEmail(), details.getUsername());
        assertTrue(details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("READ")));
    }
}
