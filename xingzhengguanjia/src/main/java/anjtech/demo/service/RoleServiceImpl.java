package anjtech.demo.service;

import anjtech.demo.dao.RoleRepository;
import anjtech.demo.po.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Page<Role> listRole(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public List<Role> listAll() {
        return roleRepository.findAll();
    }
}
