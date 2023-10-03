package com.example.banksystem.Views;

import com.example.banksystem.Controllers.Client.ClientController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    // Client View
    private AnchorPane dashboardView;
    private AnchorPane transactionView;

    public ViewFactory() {}

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getTransactionView() {
        if (transactionView == null) {
            try {
                transactionView = new FXMLLoader(getClass().getResource("/FXML/Client/Transactions.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return transactionView;
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    public void showClientWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }
        private void createStage (FXMLLoader loader){
            Scene scene = null;
            try {
                scene = new Scene(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Alobrochuk Bank");
            stage.show();
        }

        public void closeStage(Stage stage) {
            stage.close();
        }
    }
