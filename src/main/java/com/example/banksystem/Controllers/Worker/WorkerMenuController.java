package com.example.banksystem.Controllers.Worker;

import com.example.banksystem.Models.Model;
import com.example.banksystem.Views.WorkerMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkerMenuController implements Initializable {
    public Button create_client_btn;
    public Button clients_btn;
    public Button deposit_btn;
    public Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        create_client_btn.setOnAction(event -> onCreateClient());
        clients_btn.setOnAction(event -> onClients());
        deposit_btn.setOnAction(event -> onDeposit());
        logout_btn.setOnAction(event -> onLogout());
    }

    private void onCreateClient() {
        Model.getInstance().getViewFactory().getWorkerSelectedMenuItem().set(WorkerMenuOptions.CREATE_CLIENT);
    }

    private void onClients() {
        Model.getInstance().getViewFactory().getWorkerSelectedMenuItem().set(WorkerMenuOptions.CLIENTS);
    }

    private void onDeposit() {
        Model.getInstance().getViewFactory().getWorkerSelectedMenuItem().set(WorkerMenuOptions.DEPOSIT);
    }

    private void onLogout(){
        // get stage
        Stage stage = (Stage) clients_btn.getScene().getWindow();
        // close the worker window
        Model.getInstance().getViewFactory().closeStage(stage);
        // Show Login Window
        Model.getInstance().getViewFactory().showLoginWindow();
        // Set Client Login Succes Flag to false!! Чтобы клиент больше не зашел
        Model.getInstance().setWorkerLoginSuccessFlag(false);
    }
}
