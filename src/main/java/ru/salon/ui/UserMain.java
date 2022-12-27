package ru.salon.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.salon.ControllersConfiguration;
import ru.salon.entity.Record;
import ru.salon.entity.Service;
import ru.salon.entity.User;
import ru.salon.service.RecordService;
import ru.salon.service.ServiceService;
import ru.salon.util.Role;
import ru.salon.service.UserService;
import ru.salon.util.Status;
import ru.salon.util.Time;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class UserMain {

    private boolean dateChoice = false;
    private boolean employeeChoice = false;
    private boolean serviceChoice = false;

    private ObservableList<TableRecord> recordData;
    private ObservableList<Record> myRecordData;
    @Setter
    private User user;

    @Setter
    @Getter
    private Stage itStage;
    @Autowired
    private Login login;

    @Autowired
    private UserService userService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private ServiceService serviceService;
    @FXML
    private TabPane TabPaneMain;
    @FXML
    private Tab tabRecord;
    @FXML
    private Tab tabMyRecord;
    @FXML
    private SplitMenuButton splitMenuEmployee;
    @FXML
    private SplitMenuButton splitMenuDate;
    @FXML
    private Label userLabel;
    @FXML
    private TableView recordTable;
    @FXML
    private Button recordButton;
    @FXML
    private Label infoLabel;
    @FXML
    private SplitMenuButton serviceSplitMenuButton;
    @FXML
    private TableView myRecordTableView;
    @FXML
    private Button deleteRecordButton;
    @FXML
    private Label deleteInfoLabel;
    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() {
        LocalDate now = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            MenuItem menuItem = new MenuItem(now.plusDays(i).toString());
            menuItem.setOnAction((e) -> {
                splitMenuDate.setText(menuItem.getText());
                dateChoice = true;
                if (employeeChoice == true && dateChoice == true) {
                    updateRecordTable();
                }
            });
            splitMenuDate.getItems().add(menuItem);
        }


        List<User> allUserByRole = userService.findAllByRole(Role.EMPLOYEE);
        List<MenuItem> menuItems = new ArrayList<>();

        for (User userItem : allUserByRole) {
            MenuItem menuItem = new MenuItem(userItem.getFullName() + " " + userItem.getLogin());
            menuItem.setOnAction((e) -> {
                splitMenuEmployee.setText(menuItem.getText());
                employeeChoice = true;
                if (employeeChoice == true && dateChoice == true) {
                    updateRecordTable();
                }
            });
            menuItems.add(menuItem);
        }
        splitMenuEmployee.getItems().addAll(menuItems);

        List<Service> serviceList = serviceService.findAll();
        for (Service service : serviceList) {
            MenuItem menuItem = new MenuItem(service.getServiceType());
            menuItem.setOnAction((e) -> {
                serviceSplitMenuButton.setText(menuItem.getText());
                serviceChoice = true;
            });
            serviceSplitMenuButton.getItems().add(menuItem);
        }
    }

    public void show() {
        itStage.show();
    }

    public void updateRecordTable() {
        String employeeLogin = splitMenuEmployee.getText().split(" ")[3];
        System.out.println(employeeLogin);
        List<Record> records = recordService.findAllByEmployeeLoginAndRecordDate(employeeLogin, splitMenuDate.getText());
        Map<String, String> time = new HashMap<>(Time.time);
        for (String item : time.keySet()) {
            if (records.stream().anyMatch(r -> r.getRecordTime().equals(item))) {
                time.put(item, Status.Close.name());
            }
        }
        List<TableRecord> tableRecords = new ArrayList<>();
        for (String item : time.keySet()) {
            tableRecords.add(new TableRecord(item, time.get(item)));
        }
        tableRecords.sort(Comparator.comparing(TableRecord::getTime));
        System.out.println(tableRecords);

        recordData = FXCollections.observableArrayList(tableRecords);

        // Столбцы таблицы
        TableColumn<TableRecord, String> timeColumn = new TableColumn<>("Время");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<TableRecord, String> statusColumn = new TableColumn<>("Статус");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        recordTable.getColumns().setAll(timeColumn, statusColumn);

        // Данные таблицы
        recordTable.setItems(recordData);
    }

    public void updateMyRecordTable() {
        List<Record> allByUserLogin = recordService.findAllByUserLogin(user.getLogin());
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

        myRecordTableView.getColumns().setAll(id, employeeFullName, userFullName, employeeLogin, userLogin, recordDate, recordTime, serviceType);
        myRecordTableView.setItems(myRecordData);
    }

    public void addUser(User user) {
        this.user = user;
        addLabel();
    }

    public void addLabel() {
        this.userLabel.setText(user.getLogin());
    }

    @FXML
    public void getTableData(MouseEvent mouseEvent) {
        int index = recordTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
    }

    @FXML
    public void recordOn(ActionEvent actionEvent) {
        int index = recordTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            infoLabel.setText("Выберете свободное время в таблице");
        } else {
            TableRecord tableRecord = (TableRecord) recordTable.getItems().get(index);
            if (Status.Close.name().equals(tableRecord.getStatus())) {
                infoLabel.setText("Выберете свободное время в таблице");
            } else {
                if (serviceChoice == true) {
                    String employeeLogin = splitMenuEmployee.getText().split(" ")[3];
                    User employee = userService.findByLogin(employeeLogin).get();

                    Record record = Record.builder()
                            .employeeFullName(employee.getFullName())
                            .userFullName(user.getFullName())
                            .employeeLogin(employee.getLogin())
                            .userLogin(user.getLogin())
                            .recordDate(splitMenuDate.getText())
                            .recordTime(tableRecord.getTime())
                            .serviceType(serviceSplitMenuButton.getText())
                            .build();
                    recordService.save(record);
                    infoLabel.setText("Вы записаны");
                    updateRecordTable();
                } else {
                    infoLabel.setText("Выберете услугу");
                }
            }
        }

    }

    @FXML
    public void selectionMyRecord(Event event) {
        updateMyRecordTable();
        updateRecordTable();
    }

    @FXML
    public void deleteRecord(ActionEvent actionEvent) {
        int index = myRecordTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            deleteInfoLabel.setText("Выберете запись в таблице");
        } else {
            Record record = (Record) myRecordTableView.getItems().get(index);
            recordService.delete(record);
            deleteInfoLabel.setText("Запись удалена");
            updateMyRecordTable();
        }
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        itStage.hide();
        login.show();
    }


    @Data
    public static class TableRecord {

        private String time;
        private String status;

        public TableRecord(String time, String status) {
            this.time = time;
            this.status = status;
        }
    }
}
