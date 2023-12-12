package com.example.banksystem.Controllers.Worker;

import com.example.banksystem.Models.Client;
import com.example.banksystem.Models.Model;
import com.example.banksystem.Views.ClientCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {
    public ListView<Client> clients_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initClientsList();
        clients_listview.setItems(Model.getInstance().getClients());
        clients_listview.setCellFactory(e -> new ClientCellFactory());
    }

    // иниц. список клиентов
    private void initClientsList() {
        // если список пустой, загружаем список
        if (Model.getInstance().getClients().isEmpty()){
            Model.getInstance().setClients();
        }
    }
}
