package com.example.banksystem.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {
    public TextField fName_fld;
    public TextField lName_fld;
    public TextField password_fld;
    public CheckBox pAddress_box;
    public Label pAddress_lbl;
    public ChoiceBox role_selector;
    public Button create_client_btn;
    public Label error_lbl;
    public Label user_address_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
