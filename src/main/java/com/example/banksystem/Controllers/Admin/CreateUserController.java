package com.example.banksystem.Controllers.Admin;

import com.example.banksystem.Models.Model;
import com.example.banksystem.Views.AccountType;
import com.example.banksystem.Views.RoleType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {
    public TextField fName_fld;
    public TextField lName_fld;
    public TextField password_fld;
    public ChoiceBox<RoleType> role_selector;
    public Label error_lbl;
    public Button create_user_btn;
    public Label username_lbl;
    public CheckBox username_box;

    private String username;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_user_btn.setOnAction(event -> createUser());
        role_selector.setItems(FXCollections.observableArrayList(RoleType.СЛУЖАЩИЙ, RoleType.КОНСУЛЬТАНТ));
        username_box.selectedProperty().addListener((observableValue, oldVal, newVal) ->
        {
            if (newVal) {
                username = createUsername();
                onCreateUsername();
            }
        });
    }

    private void createUser() {
        String fName = fName_fld.getText();
        String lName = lName_fld.getText();
        String username = username_lbl.getText();
        String password = password_fld.getText();
        RoleType role = role_selector.getValue();
        Model.getInstance().getDatabaseDriver().createUser(fName, lName, username, password, String.valueOf(role));
        error_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold");
        error_lbl.setText("Пользователь успешно создан");
        emptyFields();
    }

    private void onCreateUsername() {
        if (fName_fld.getText() != null & lName_fld.getText() != null) {
            username_lbl.setText(username);
        }
    }

    private String createUsername() {
        int id = Model.getInstance().getDatabaseDriver().getLastUserID() + 1;
        char fChar = Character.toLowerCase(fName_fld.getText().charAt(0));
        return "@" + fChar + lName_fld.getText() + id;

    }

    private void emptyFields() {
        fName_fld.setText("");
        lName_fld.setText("");
        password_fld.setText("");
        username_box.setSelected(false);
    }

}
