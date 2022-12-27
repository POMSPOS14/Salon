package ru.salon;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.salon.ui.*;

import java.io.IOException;
import java.io.InputStream;


@Configuration
public class ControllersConfiguration {

    @Bean(name = "mainView")
    public ViewHolder getMainView() throws IOException {
        return loadView("fxml/main.fxml");
    }

    @Bean(name = "loginView")
    public ViewHolder getLoginView() throws IOException {
        return loadView("fxml/login.fxml");
    }

    @Bean(name = "registrationView")
    public ViewHolder getRegistrationView() throws IOException {
        return loadView("fxml/registration.fxml");
    }
    @Bean(name = "userMainView")
    public ViewHolder getUserMainView() throws IOException {
        return loadView("fxml/userMain.fxml");
    }

    @Bean(name = "employeeMainView")
    public ViewHolder getEmployeeMainView() throws IOException {
        return loadView("fxml/employeeMain.fxml");
    }
    @Bean(name = "adminMainView")
    public ViewHolder getAdminMainView() throws IOException {
        return loadView("fxml/adminMain.fxml");
    }

    @Bean
    public MainController getMainController() throws IOException {
        return (MainController) getMainView().getController();
    }

    @Bean
    public EmployeeMain getEmployeeMainController() throws IOException {
        return (EmployeeMain) getEmployeeMainView().getController();
    }
    @Bean
    public AdminMain getAdminMainController() throws IOException {
        return (AdminMain) getAdminMainView().getController();
    }

    @Bean
    public Login getLoginController() throws IOException {
        return (Login) getLoginView().getController();
    }

    @Bean
    public Registration getRegistrationController() throws IOException {
        return (Registration) getRegistrationView().getController();
    }

    @Bean
    public UserMain getUserMainController() throws IOException {
        return (UserMain) getUserMainView().getController();
    }

    /**
     * Самый обыкновенный способ использовать FXML загрузчик.
     * Как раз-таки на этом этапе будет создан объект-контроллер,
     * произведены все FXML инъекции и вызван метод инициализации контроллера.
     */
    protected ViewHolder loadView(String url) throws IOException {
        InputStream fxmlStream = null;
        try {
            fxmlStream = getClass().getClassLoader().getResourceAsStream(url);
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return new ViewHolder(loader.getRoot(), loader.getController());
        } finally {
            if (fxmlStream != null) {
                fxmlStream.close();
            }
        }
    }

    /**
     * Класс - оболочка: контроллер мы обязаны указать в качестве бина,
     * а view - представление, нам предстоит использовать в точке входа {@link Application}.
     */
    public class ViewHolder {
        private Parent view;
        private Object controller;

        public ViewHolder(Parent view, Object controller) {
            this.view = view;
            this.controller = controller;
        }

        public Parent getView() {
            return view;
        }

        public void setView(Parent view) {
            this.view = view;
        }

        public Object getController() {
            return controller;
        }

        public void setController(Object controller) {
            this.controller = controller;
        }
    }

}
