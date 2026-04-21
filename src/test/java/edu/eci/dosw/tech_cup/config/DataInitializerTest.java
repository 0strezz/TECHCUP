package edu.eci.dosw.tech_cup.config;

import edu.eci.dosw.tech_cup.entity.user.PermissionEntity;
import edu.eci.dosw.tech_cup.entity.user.RoleEntity;
import edu.eci.dosw.tech_cup.repository.PermissionRepository;
import edu.eci.dosw.tech_cup.repository.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DataInitializer tests")
class DataInitializerTest {

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    void run_WhenDataExists_DoesNothing() throws Exception {
        when(permissionRepository.findByName(anyString())).thenReturn(Optional.of(new PermissionEntity("dummy", "description")));
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(new RoleEntity("dummy", "description")));

        dataInitializer.run();

        verify(roleRepository, never()).save(any());
        verify(permissionRepository, never()).save(any());
    }

    @Test
    void run_WhenNoData_CreatesRolesAndAdmin() throws Exception {
        when(permissionRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());
        
        when(permissionRepository.save(any(PermissionEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(roleRepository.save(any(RoleEntity.class))).thenAnswer(i -> i.getArguments()[0]);


        dataInitializer.run();

        // Check it creates permissions and roles out of the ENUMs
        verify(permissionRepository, atLeastOnce()).save(any());
        verify(roleRepository, atLeastOnce()).save(any());
    }
}
