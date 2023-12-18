package com.example.banksystem.Views;

import com.example.banksystem.Controllers.Admin.UserCellController;
import com.example.banksystem.Models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class UserCellFactory extends ListCell<User> {


    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/UserCell.fxml"));
            UserCellController controller = new UserCellController(user);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
