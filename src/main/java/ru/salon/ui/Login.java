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
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.salon.ControllersConfiguration;
import ru.salon.entity.User;
import ru.salon.util.Role;
import ru.salon.service.UserService;

import java.util.Optional;

@Data
@SuppressWarnings("SpringJavaAutowiringInspection")
public class Login {

    @Qualifier("userMainView")
    @Autowired
    private ControllersConfiguration.ViewHolder userMainView;
    @Qualifier("adminMainView")
    @Autowired
    private ControllersConfiguration.ViewHolder adminMainView;

    @Qualifier("employeeMainView")
    @Autowired
    private ControllersConfiguration.ViewHolder employeeMainView;

    @Qualifier("registrationView")
    @Autowired
    private ControllersConfiguration.ViewHolder registrationView;

    @Autowired
    private UserService userService;

    @Autowired
    private MainController mainController;

    @Autowired
    private UserMain userMain;

    @Autowired
    private EmployeeMain employeeMain;

    @Autowired
    private AdminMain adminMain;

    @Autowired
    private Registration registration;

    @Setter
    @Getter
    private Stage itStage;
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

    public void show() {
        itStage.show();
    }

    @FXML
    public void login(ActionEvent event) {
        String loginTextFieldText = loginTextField.getText();
        String passwordTextFieldText = passwordTextField.getText();
        Optional<User> user = userService.findByLoginAndPassword(loginTextFieldText, passwordTextFieldText);
        this.itStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        if (user.isPresent()) {
            if (user.get().getRole().equals(Role.USER)) {
                if (userMain.getItStage() == null) {
                    Stage stage = new Stage();
                    stage.setScene(new Scene(userMainView.getView(), stage.getWidth(), stage.getHeight()));
                    stage.setResizable(true);
                    stage.centerOnScreen();
                    stage.show();
                    userMain.addUser(user.get());
                    userMain.setItStage(stage);
                } else {
                    userMain.show();
                }
            }
            if (user.get().getRole().equals(Role.EMPLOYEE)) {
                if (employeeMain.getItStage() == null) {
                    Stage stage = new Stage();
                    stage.setScene(new Scene(employeeMainView.getView(), stage.getWidth(), stage.getHeight()));
                    stage.setResizable(true);
                    stage.centerOnScreen();
                    stage.show();
                    employeeMain.addUser(user.get());
                    employeeMain.updateMyRecordTable();
                    employeeMain.setItStage(stage);
                } else {
                    employeeMain.show();
                }
            }
            if (user.get().getRole().equals(Role.ADMIN)) {
                if (adminMain.getItStage() == null) {
                    Stage stage = new Stage();
                    stage.setScene(new Scene(adminMainView.getView(), stage.getWidth(), stage.getHeight()));
                    stage.setResizable(true);
                    stage.centerOnScreen();
                    stage.show();
                    adminMain.addUser(user.get());
                    adminMain.updateUserTable();
                    adminMain.setItStage(stage);
                } else {
                    adminMain.show();
                }
            }
            itStage.hide();
        } else {
            infoLabel.setText("Пользователь не найден");
        }
    }

    @FXML
    public void registrationAction(ActionEvent actionEvent) {
        if (registration.getItStage() == null) {
            Stage stage = new Stage();
            stage.setTitle("Регистрация");
            stage.setScene(new Scene(registrationView.getView()));
            stage.setResizable(true);
            stage.centerOnScreen();
            stage.show();
            registration.setItStage(stage);
        } else {
            registration.show();
        }
    }
}
