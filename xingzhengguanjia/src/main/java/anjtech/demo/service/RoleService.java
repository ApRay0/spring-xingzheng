package anjtech.demo.service;

import anjtech.demo.po.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);

    void deleteRole(Long id);

    Page<Role> listRole(Pageable pageable);

    Role findByName(String name);

    List<Role> listAll();

}
