package com.example.banksystem.Controllers.Worker;

import com.example.banksystem.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkerController implements Initializable {
    public BorderPane worker_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getWorkerSelectedMenuItem().addListener((observableValue, s, newVal) -> {
            switch (newVal) {
                case CLIENTS -> worker_parent.setCenter(Model.getInstance().getViewFactory().getClientsView());
                case DEPOSIT -> worker_parent.setCenter(Model.getInstance().getViewFactory().getDepositView());
                default -> worker_parent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
            }
            });
        }
    }
