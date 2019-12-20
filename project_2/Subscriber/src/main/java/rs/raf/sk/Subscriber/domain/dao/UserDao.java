package rs.raf.sk.Subscriber.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.raf.sk.Subscriber.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

        List<User> findByEmailEndsWith (String sufix);

        Optional<User> findByUsername (String username);

        Optional<User> findByUsernameAndPassword (String username, String password);

}
