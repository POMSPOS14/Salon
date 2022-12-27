package ru.salon.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Data;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.salon.entity.User;
import ru.salon.util.Role;
import ru.salon.service.UserService;

import java.util.Optional;

@Data
@SuppressWarnings("SpringJavaAutowiringInspection")
public class Registration {

    @Autowired
    private UserService userService;

    @Setter
    @Getter
    private Stage itStage;
    @FXML
    private TextField firstNameInfoTextField;
    @FXML
    private TextField secondNameInfoTextField;
    @FXML
    private TextField loginInfoTextField;
    @FXML
    private Button registration;
    @FXML
    private Label infoLabel;
    @FXML
    private TextField middleNameInfoTextField;
    @FXML
    private PasswordField passwordInfoTextField;

    @FXML
    public void initialize() {

    }

    public void show() {
        itStage.show();
    }
    @FXML
    public void registrationAction(ActionEvent actionEvent) {

        String firstNameInfoTextFieldText = firstNameInfoTextField.getText();
        String secondNameInfoTextFieldText = secondNameInfoTextField.getText();
        String middleNameInfoTextFieldText = middleNameInfoTextField.getText();
        String loginInfoTextFieldText = loginInfoTextField.getText();
        String passwordInfoTextFieldText = passwordInfoTextField.getText();

        Optional<User> user = userService.findByLogin(loginInfoTextFieldText);

        if (user.isPresent()) {
            infoLabel.setText("Пользователь c таким логином уже существует");
        }else {
            userService.save(User.builder()
                    .firstName(firstNameInfoTextFieldText)
                    .secondName(secondNameInfoTextFieldText)
                    .middleName(middleNameInfoTextFieldText)
                    .login(loginInfoTextFieldText)
                    .password(passwordInfoTextFieldText)
                    .role(Role.USER).build());
            infoLabel.setText("Регистрация успешна, можете закрывать окно");
        }

    }
}
