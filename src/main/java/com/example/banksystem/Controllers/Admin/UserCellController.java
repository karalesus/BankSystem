package com.example.banksystem.Controllers.Admin;

import com.example.banksystem.Models.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class UserCellController implements Initializable {
    public Label fName_lbl;
    public Label lName_lbl;
    public Label username_lbl;
    public Label role_lbl;
    public Button delete_btn;

    private final User user;
    public Label info_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public UserCellController(User user) {
        this.user = user;
    }
}
