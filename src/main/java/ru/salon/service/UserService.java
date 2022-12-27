package ru.salon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.salon.entity.User;
import ru.salon.repository.UserRepository;
import ru.salon.util.Role;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Метод добавляет парочку записей в БД после запуска приложения,
     * чтобы не было совсем пусто.
     *
     * Из-за того, что подключена H2 (in-memory) БД.
     */
    @PostConstruct
    public void generateTestData() {

        save(User.builder()
                .firstName("Пользователь")
                .secondName("Пользов")
                .middleName("Пользователич")
                .login("1")
                .password("1")
                .role(Role.USER).build());

        save(User.builder()
                .firstName("Работник")
                .secondName("Работов")
                .middleName("Раблтович")
                .login("2")
                .password("2")
                .role(Role.EMPLOYEE).build());

        save(User.builder()
                .firstName("Адми")
                .secondName("Админов")
                .middleName("Админович")
                .login("3")
                .password("3")
                .role(Role.ADMIN).build());

        save(User.builder()
                .firstName("Иван")
                .secondName("Иванов")
                .middleName("Иванович")
                .login("allure_admin")
                .password("allure_admin123@@@")
                .role(Role.ADMIN).build());

        save(User.builder()
                .firstName("Пётр")
                .secondName("Петров")
                .middleName("Петрович")
                .login("allure_staff1")
                .password("VOIdbgHd")
                .role(Role.EMPLOYEE).build());

        save(User.builder()
                .firstName("Пётр")
                .secondName("Петров")
                .middleName("Петрович")
                .login("allure_staff2")
                .password("spJCyWtt")
                .role(Role.EMPLOYEE).build());

        save(User.builder()
                .firstName("Пётр")
                .secondName("Петров")
                .middleName("Петрович")
                .login("allure_staff3")
                .password("SyzDBqdJ")
                .role(Role.EMPLOYEE).build());

        save(User.builder()
                .firstName("Пётр")
                .secondName("Петров")
                .middleName("Петрович")
                .login("allure_staff4")
                .password("fJEWeQtl")
                .role(Role.EMPLOYEE).build());

        save(User.builder()
                .firstName("Пётр")
                .secondName("Петров")
                .middleName("Петрович")
                .login("allure_staff5")
                .password("allure_staff5")
                .role(Role.EMPLOYEE).build());

    }


    public User save(User user) {
        return repository.save(user);
    }


    public List<User> findAll() {
        return repository.findAll();
    }
    public List<User> findAllByRole(Role role) {
        return repository.findAllByRole(role);
    }

    public Optional<User> findByLoginAndPassword(String login, String password){
        return repository.findByLoginAndPassword(login, password);
    }

    public Optional<User> findByLogin(String login){
        return repository.findByLogin(login);
    }
}
