package ru.salon.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.salon.entity.Record;
import ru.salon.entity.User;
import ru.salon.service.RecordService;
import ru.salon.service.UserService;
import ru.salon.util.Role;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("SpringJavaAutowiringInspection")
public class AdminMain {

    @Autowired
    private Login login;

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService userService;

    @Setter
    @Getter
    private Stage itStage;

    @Setter
    private User user;

    private ObservableList<Record> recordData;
    private ObservableList<User> userData;

    @FXML
    private TableView userTableView;
    @FXML
    private TextField firstNameInfoTextField;
    @FXML
    private TextField secondNameInfoTextField;
    @FXML
    private TextField middleNameInfoTextField;
    @FXML
    private TextField loginInfoTextField;
    @FXML
    private TextField passwordInfoTextField;
    @FXML
    private Label idInfoTextField;
    @FXML
    private Button changeDataButton;
    @FXML
    private TextField roleInfoTextField;
    @FXML
    private TableView recordTableView;
    @FXML
    private SplitMenuButton splitMenuEmployee;
    @FXML
    private Label userLabel;
    @FXML
    private Button exitButton;

    @PostConstruct
    public void init() {
        List<User> allUserByRole = userService.findAllByRole(Role.EMPLOYEE);
        List<MenuItem> menuItems = new ArrayList<>();

        for (User userItem : allUserByRole) {
            MenuItem menuItem = new MenuItem(userItem.getFullName() + " " + userItem.getLogin());
            menuItem.setOnAction((e) -> {
                splitMenuEmployee.setText(menuItem.getText());
                updateRecordTable();
            });
            menuItems.add(menuItem);
        }
        splitMenuEmployee.getItems().addAll(menuItems);
    }

    public void show() {
        itStage.show();
    }

    public void updateUserTable(){
        List<User> users = userService.findAll();
        userData = FXCollections.observableArrayList(users);

        // Столбцы таблицы
        TableColumn<User, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> firstName = new TableColumn<>("Имя");
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<User, String> secondName = new TableColumn<>("Фамилия");
        secondName.setCellValueFactory(new PropertyValueFactory<>("secondName"));

        TableColumn<User, String> middleName = new TableColumn<>("Отчество");
        middleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));

        TableColumn<User, String> login = new TableColumn<>("Логин");
        login.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<User, String> role = new TableColumn<>("Роль");
        role.setCellValueFactory(new PropertyValueFactory<>("role"));

        userTableView.getColumns().setAll(idColumn, firstName, secondName, middleName, login, role);

        // Данные таблицы
        userTableView.setItems(userData);
    }

    public void updateRecordTable() {
        String employeeLoginSearch = splitMenuEmployee.getText().split(" ")[3];
        List<Record> allByUserLogin = recordService.findAllByEmployeeLogin(employeeLoginSearch);
        recordData = FXCollections.observableArrayList(allByUserLogin);

        TableColumn<Record, String> id = new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Record, String> employeeFullName = new TableColumn<>("Мастер");
        employeeFullName.setCellValueFactory(new PropertyValueFactory<>("employeeFullName"));

        TableColumn<Record, String> userFullName = new TableColumn<>("Клиент");
        userFullName.setCellValueFactory(new PropertyValueFactory<>("userFullName"));

        TableColumn<Record, String> employeeLogin = new TableColumn<>("Логин М");
        employeeLogin.setCellValueFactory(new PropertyValueFactory<>("employeeLogin"));

        TableColumn<Record, String> userLogin = new TableColumn<>("Логин К");
        userLogin.setCellValueFactory(new PropertyValueFactory<>("userLogin"));

        TableColumn<Record, String> recordDate = new TableColumn<>("Дата");
        recordDate.setCellValueFactory(new PropertyValueFactory<>("recordDate"));

        TableColumn<Record, String> recordTime = new TableColumn<>("Время");
        recordTime.setCellValueFactory(new PropertyValueFactory<>("recordTime"));

        TableColumn<Record, String> serviceType = new TableColumn<>("Услуга");
        serviceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));

        recordTableView.getColumns().setAll(id, employeeFullName, userFullName, employeeLogin, userLogin, recordDate, recordTime, serviceType);
        recordTableView.setItems(recordData);
    }

    public void addUser(User user) {
        this.user = user;
        addLabel();
    }

    public void addLabel() {
        this.userLabel.setText(user.getLogin());
    }
    @FXML
    public void changeData(ActionEvent actionEvent) {
        String idInfoTextFieldText = idInfoTextField.getText();
        String firstNameInfoTextFieldText = firstNameInfoTextField.getText();
        String secondNameInfoTextFieldText = secondNameInfoTextField.getText();
        String middleNameInfoTextFieldText = middleNameInfoTextField.getText();
        String loginInfoTextFieldText = loginInfoTextField.getText();
        String passwordInfoTextFieldText = passwordInfoTextField.getText();
        String roleInfoTextFieldText = roleInfoTextField.getText();

        User userSave = User.builder()
                .id(Long.valueOf(idInfoTextFieldText))
                .firstName(firstNameInfoTextFieldText)
                .secondName(secondNameInfoTextFieldText)
                .middleName(middleNameInfoTextFieldText)
                .login(loginInfoTextFieldText)
                .password(passwordInfoTextFieldText)
                .role(Role.valueOf(roleInfoTextFieldText))
                .build();
        userService.save(userSave);
        updateUserTable();
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        itStage.hide();
        login.show();
    }

    @FXML
    public void getTableData(MouseEvent event) {
        int index = userTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        User userTouch = (User) userTableView.getItems().get(index);
        idInfoTextField.setText(userTouch.getId().toString());
        firstNameInfoTextField.setText(userTouch.getFirstName());
        secondNameInfoTextField.setText(userTouch.getSecondName());
        middleNameInfoTextField.setText(userTouch.getMiddleName());
        loginInfoTextField.setText(userTouch.getLogin());
        passwordInfoTextField.setText(userTouch.getPassword());
        roleInfoTextField.setText(userTouch.getRole().name());
    }
}
