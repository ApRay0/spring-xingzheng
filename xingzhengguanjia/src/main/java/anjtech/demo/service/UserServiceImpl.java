package anjtech.demo.service;

import anjtech.demo.dao.UserRepository;
import anjtech.demo.po.Role;
import anjtech.demo.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public User checkUser(String email, String password) {

        User user = userRepository.findByEmailAndPassword(email, password);

        return user;
    }

    @Override
    public Page<User> listByQuery(String query, Pageable pageable) {
        return userRepository.findByQuery(query, pageable);
    }

    @Override
    public Page<User> listAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<Role> getRoles(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        return user.getRoles();
    }
}
