package com.example.banksystem.Controllers.Admin;

import com.example.banksystem.Models.Model;
import com.example.banksystem.Views.AdminMenuOptions;
import com.example.banksystem.Views.WorkerMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button create_btn;
    public Button accounts_btn;
    public Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        create_btn.setOnAction(event -> onCreateUser());
        accounts_btn.setOnAction(event -> onUsers());
        logout_btn.setOnAction(event -> onLogout());
    }

    private void onCreateUser() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_USER);
    }

    private void onUsers() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.USERS);
    }

    private void onLogout(){
        // get stage
        Stage stage = (Stage) create_btn.getScene().getWindow();
        // close the worker window
        Model.getInstance().getViewFactory().closeStage(stage);
        // Show Login Window
        Model.getInstance().getViewFactory().showLoginWindow();
        // Set Client Login Success Flag to false!! Чтобы клиент больше не зашел
        Model.getInstance().setAdminLoginSuccessFlag(false);
    }
}
