package edu.eci.dosw.tech_cup.config;

import edu.eci.dosw.tech_cup.entity.user.PermissionEntity;
import edu.eci.dosw.tech_cup.entity.user.RoleEntity;
import edu.eci.dosw.tech_cup.repository.PermissionRepository;
import edu.eci.dosw.tech_cup.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public DataInitializer(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void run(String... args) {
        PermissionEntity userRead = createPermissionIfNotExists("USER_READ", "Read users");
        PermissionEntity userUpdate = createPermissionIfNotExists("USER_UPDATE", "Update users");
        PermissionEntity tournamentCreate = createPermissionIfNotExists("TOURNAMENT_CREATE", "Create tournaments");
        PermissionEntity tournamentUpdate = createPermissionIfNotExists("TOURNAMENT_UPDATE", "Update tournaments");
        PermissionEntity teamManage = createPermissionIfNotExists("TEAM_MANAGE", "Manage teams");

        createRoleIfNotExists("PLAYER", "Basic player role", List.of());
        createRoleIfNotExists("CAPTAIN", "Captain role", List.of(teamManage));
        createRoleIfNotExists("REFEREE", "Referee role", List.of());
        createRoleIfNotExists("ORGANIZER", "Organizer role", List.of(tournamentCreate, tournamentUpdate));
        createRoleIfNotExists("ADMINISTRATOR", "Administrator role",
                List.of(userRead, userUpdate, tournamentCreate, tournamentUpdate, teamManage));
    }

    private PermissionEntity createPermissionIfNotExists(String name, String description) {
        return permissionRepository.findByName(name)
                .orElseGet(() -> permissionRepository.save(new PermissionEntity(name, description)));
    }

    private void createRoleIfNotExists(String name, String description, List<PermissionEntity> permissions) {
        if (roleRepository.findByName(name).isEmpty()) {
            RoleEntity role = new RoleEntity(name, description);
            role.setPermissions(permissions);
            roleRepository.save(role);
        }
    }
}