package com.example.banksystem.Controllers.Worker;

import com.example.banksystem.Models.Client;
import com.example.banksystem.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable {

    public Label fName_lbl;
    public Label lName_lbl;
    public Label pAddress_lbl;
    public Label ch_acc_lbl;
    public Label sv_acc_lbl;
    public Label date_lbl;
    public Button delete_btn;
    public Label info_lbl;


    private final Client client;

    public ClientCellController(Client client){
        this.client = client;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fName_lbl.textProperty().bind(client.firstNameProperty());
        lName_lbl.textProperty().bind(client.lastNameProperty());
        pAddress_lbl.textProperty().bind(client.pAddressProperty());
        ch_acc_lbl.textProperty().bind(client.checkingAccountProperty().asString());
        sv_acc_lbl.textProperty().bind(client.savingsAccountProperty().asString());
        date_lbl.textProperty().bind(client.dateProperty().asString());
        delete_btn.setOnAction(event -> onDelete());
//        delete_btn.setOnAction(event -> onDelete());
    }

    public void onDelete() {
        Model.getInstance().getDatabaseDriver().deleteClient(pAddress_lbl.getText());
        info_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.0em; -fx-font-weight: bold");
        info_lbl.setText("Клиент удален");
    }
}
