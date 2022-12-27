package ru.salon;

import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.salon.entity.User;
import ru.salon.repository.UserRepository;
import ru.salon.util.Role;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {

    @Autowired
    private UserRepository repository;

    @BeforeClass
    public static void bootstrapJavaFx(){
        new JFXPanel();
    }

    @Test
    public void saveUserTest() {
        User user = User.builder()
                .firstName("Иван")
                .secondName("Иванов")
                .middleName("Иванович")
                .login("allure_admin_test")
                .password("allure_admin123@@@")
                .role(Role.ADMIN).build();

        User save = repository.save(user);
        assertThat(save).isEqualTo(user);
    }

}