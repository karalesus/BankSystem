package com.example.banksystem.Controllers.Admin;

import com.example.banksystem.Models.Model;
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
            fName_lbl.textProperty().bind(user.firstNameProperty());
            lName_lbl.textProperty().bind(user.lastNameProperty());
            username_lbl.textProperty().bind(user.usernameProperty());
            role_lbl.textProperty().bind(user.roleProperty());
            delete_btn.setOnAction(event -> onDelete());
        }

        public void onDelete() {
            Model.getInstance().getDatabaseDriver().deleteUser(username_lbl.getText());
            info_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.0em; -fx-font-weight: bold");
            info_lbl.setText("Пользователь удален");
        }


    public UserCellController(User user) {
        this.user = user;
    }
}
