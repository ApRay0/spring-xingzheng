package anjtech.demo.dao;

import anjtech.demo.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    @Query("select u from User u where u.email like ?1")
    Page<User> findByQuery(String query, Pageable pageable);
}
