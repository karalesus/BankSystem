package com.example.banksystem.Controllers.Consultant;

import com.example.banksystem.Models.Model;
import com.example.banksystem.Views.AdminMenuOptions;
import com.example.banksystem.Views.ConsultantMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConsultantMenuController implements Initializable {
    public Button questions_btn;
    public Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        questions_btn.setOnAction(event -> onOpenQuestions());
        logout_btn.setOnAction(event -> onLogout());
    }

    private void onOpenQuestions() {
        Model.getInstance().getViewFactory().getConsultantSelectedMenuItem().set(ConsultantMenuOptions.QUESTIONBOARD);
    }

    private void onLogout(){
        // get stage
        Stage stage = (Stage) questions_btn.getScene().getWindow();
        // close the worker window
        Model.getInstance().getViewFactory().closeStage(stage);
        // Show Login Window
        Model.getInstance().getViewFactory().showLoginWindow();
        // Set Client Login Success Flag to false!! Чтобы клиент больше не зашел
        Model.getInstance().setAdminLoginSuccessFlag(false);
    }
}
