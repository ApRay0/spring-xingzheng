package anjtech.demo.service;

import anjtech.demo.po.Role;
import anjtech.demo.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User checkUser(String email, String password);

    List<Role> getRoles(String email, String password);


    Page<User> listByQuery(String query, Pageable pageable);

    Page<User> listAll(Pageable pageable);

    User saveUser(User user);

    void deleteUser(Long id);

    User findByEmail(String email);

    User findById(Long id);
}
