package ru.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.salon.entity.User;
import ru.salon.util.Role;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();
    List<User> findAllByRole(Role role);

    Optional<User> findByLoginAndPassword(String login, String password);
    Optional<User> findByLogin(String login);

}
