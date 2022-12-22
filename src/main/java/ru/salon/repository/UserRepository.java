package ru.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.salon.entity.User;

import java.util.List;
import java.util.Optional;


@Transactional(propagation = Propagation.MANDATORY)
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Optional<User> findByLoginAndPassword(String login, String password);
    Optional<User> findByLogin(String login);

}
