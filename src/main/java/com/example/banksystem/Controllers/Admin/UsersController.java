package com.example.banksystem.Controllers.Admin;

import com.example.banksystem.Models.Client;
import com.example.banksystem.Models.Model;
import com.example.banksystem.Models.User;
import com.example.banksystem.Views.ClientCellFactory;
import com.example.banksystem.Views.UserCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    public ListView<User> users_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initUsersList();
        users_listview.setItems(Model.getInstance().getUsers());
        users_listview.setCellFactory(e -> new UserCellFactory());
    }

    // иниц. список клиентов
    private void initUsersList() {
        // если список пустой, загружаем список
        if (Model.getInstance().getUsers().isEmpty()){
            Model.getInstance().setUsers();
        }
    }
}


