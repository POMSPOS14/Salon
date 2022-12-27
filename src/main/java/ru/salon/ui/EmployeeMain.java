package ru.salon.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.salon.ControllersConfiguration;
import ru.salon.entity.Record;
import ru.salon.entity.User;
import ru.salon.service.RecordService;
import ru.salon.service.UserService;
import ru.salon.util.Role;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;


@SuppressWarnings("SpringJavaAutowiringInspection")
public class EmployeeMain {

    @Setter
    private User user;

    @Autowired
    private Login login;
    @Autowired
    private RecordService recordService;

    private ObservableList<Record> myRecordData;

    @FXML
    private TableView myRecordTable;
    @FXML
    private Label userLabel;
    @FXML
    private Button exitButton;

    @Setter
    @Getter
    private Stage itStage;


    @PostConstruct
    public void init() {

    }

    public void show() {
        itStage.show();
    }

    public void updateMyRecordTable(){
        List<Record> allByUserLogin = recordService.findAllByEmployeeLogin(user.getLogin());
        myRecordData = FXCollections.observableArrayList(allByUserLogin);

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

        myRecordTable.getColumns().setAll(id, employeeFullName, userFullName, employeeLogin, userLogin, recordDate, recordTime, serviceType);
        myRecordTable.setItems(myRecordData);
    }

    public void addUser(User user) {
        this.user = user;
        addLabel();
    }

    public void addLabel() {
        this.userLabel.setText(user.getLogin());
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        itStage.hide();
        login.show();
    }
}
