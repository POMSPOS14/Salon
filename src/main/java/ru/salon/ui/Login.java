package ru.salon.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.salon.ControllersConfiguration;
import ru.salon.entity.User;
import ru.salon.service.UserService;

import java.util.Optional;

@Data
@SuppressWarnings("SpringJavaAutowiringInspection")
public class Login {

    @Qualifier("mainView")
    @Autowired
    private ControllersConfiguration.ViewHolder mainView;

    @Qualifier("registrationView")
    @Autowired
    private ControllersConfiguration.ViewHolder registrationView;

    @Autowired
    private UserService userService;

    @Autowired
    private MainController mainController;
    @FXML
    private Button inButton;
    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label loginLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private ImageView mainImageView;
    @FXML
    private Label infoLabel;
    @FXML
    private Button registrationButton;

    @FXML
    public void initialize() {
    }

    @FXML
    public void login(ActionEvent event) {
        String loginTextFieldText = loginTextField.getText();
        String passwordTextFieldText = passwordTextField.getText();
        Optional<User> user = userService.findByLoginAndPassword(loginTextFieldText, passwordTextFieldText);
        if (user.isPresent()) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainView.getView(), stage.getWidth(), stage.getHeight()));
            stage.setResizable(true);
            stage.centerOnScreen();
            stage.show();
            mainController.addUser(user.get());
            mainController.init();
        }else {
            infoLabel.setText("Пользователь не найден");
        }
    }

    @FXML
    public void registrationAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setTitle("Регистрация");
        stage.setScene(new Scene(registrationView.getView()));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }
}
