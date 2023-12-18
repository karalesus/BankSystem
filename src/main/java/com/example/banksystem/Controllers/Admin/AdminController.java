package com.example.banksystem.Controllers.Admin;

import com.example.banksystem.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.banksystem.Views.AdminMenuOptions.USERS;

public class AdminController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue, s, newVal) -> {
            switch (newVal) {
                case USERS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getUsersView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateUserView());
            }
        });
    }
}

