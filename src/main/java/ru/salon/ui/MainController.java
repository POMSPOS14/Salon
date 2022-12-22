package ru.salon.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import ru.salon.entity.User;
import ru.salon.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;


@SuppressWarnings("SpringJavaAutowiringInspection")
public class MainController {

    @Setter
    private User user;
    // Инъекции Spring
    @Autowired
    private UserService userService;

    // Инъекции JavaFX
    @FXML
    private TableView<User> table;

    // Variables
    private ObservableList<User> data;
    @FXML
    private Label userLabel;


    /**
     * Инициализация контроллера от JavaFX.
     * Метод вызывается после того как FXML загрузчик произвел инъекции полей.
     * <p>
     * Обратите внимание, что имя метода <b>обязательно</b> должно быть "initialize",
     * в противном случае, метод не вызовется.
     * <p>
     * Также на этом этапе еще отсутствуют бины спринга
     * и для инициализации лучше использовать метод,
     * описанный аннотацией @PostConstruct,
     * который вызовется спрингом, после того, как им будут произведены все инъекции.
     * {@link MainController#init()}
     */
    @FXML
    public void initialize() {
        // Этап инициализации JavaFX
    }

    /**
     * На этом этапе уже произведены все возможные инъекции.
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        List<User> users = userService.findAll();
        data = FXCollections.observableArrayList(users);

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

        table.getColumns().setAll(idColumn, firstName, secondName, middleName, login, role);

        // Данные таблицы
        table.setItems(data);
    }

    public void addUser(User user){
        this.user = user;
        addLabel();
    }
    public void addLabel(){
        this.userLabel.setText(user.getLogin());
        System.out.println(user.getLogin());
    }

    /**
     * Метод, вызываемый при нажатии на кнопку "Добавить".
     * Привязан к кнопке в FXML файле представления.
     */
    @FXML
    public void addContact() {
//        String name = txtName.getText();
//        String phone = txtPhone.getText();
//        String email = txtEmail.getText();
//        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(email)) {
//            return;
//        }
//
//        User user = new User(name, phone, email);
//        userService.save(user);
//        data.add(user);
//
//        // чистим поля
//        txtName.setText("");
//        txtPhone.setText("");
//        txtEmail.setText("");
        addLabel();
    }
}
