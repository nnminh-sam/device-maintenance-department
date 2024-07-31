package ptithcm.device_maintenance.role;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role createRole(String roleName) {
        Role role = Role.builder().name(roleName).build();
        return roleRepository.save(role);
    }

    public void deleteRole(int roleId) throws BadRequestException {
        Optional<Role> deletingRole = roleRepository.findById(roleId);
        if (deletingRole.isEmpty()) {
            throw new BadRequestException("Cannot find any role with the given ID");
        }
        Role deletedRole = deletingRole.get();
        deletedRole.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        roleRepository.save(deletedRole);
    }
}
