package com.example.banksystem.Controllers;

import com.example.banksystem.Models.Model;
import com.example.banksystem.Views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> acc_selector;
    public Label login_lbl;
    public TextField login_fld;
    public Label password_lbl;
    public TextField password_fld;
    public Button login_btn;
    public Label error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.WORKER));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acc_selector.valueProperty().addListener(observable -> setAcc_selector());
        login_btn.setOnAction(event -> onLogin());
    }

    private void onLogin() {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT) {
            // Инициализируем Client Login Credentials
            Model.getInstance().evaluateClientCred(login_fld.getText(), password_fld.getText());
            if (Model.getInstance().getClientLoginSuccessFlag()) {
                Model.getInstance().getViewFactory().showClientWindow();
                // Закрываем login stage
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                login_fld.setText("");
                password_fld.setText("");
                error_lbl.setText("Такого пользователя не существует");
            }
        } else {
            // Инициализируем Worker Login Credentials
            Model.getInstance().evaluateWorkerCred(login_fld.getText(), password_fld.getText());
            if (Model.getInstance().getWorkerLoginSuccessFlag()) {
                Model.getInstance().getViewFactory().showWorkerWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
            }else{
                login_fld.setText("");
                password_fld.setText("");
                error_lbl.setText("Такого пользователя не существует");
            }
        }
    }

    // поменять payeeAddress на Username в окошке логина для Worker, Admin, Consultant
    private void setAcc_selector() {
        Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue());
        if (acc_selector.getValue() == AccountType.WORKER){
            login_lbl.setText("Имя служащего:");
        }
//        else if (acc_selector.getValue() == AccountType.ADMIN){
//            login_lbl.setText("Имя администратора:");
//        }
//        else if (acc_selector.getValue() == AccountType.CONSULTANT){
//            login_lbl.setText("Имя консультанта:");
//        }
        else if (acc_selector.getValue() == AccountType.CLIENT){
            login_lbl.setText("Имя клиента:");
        }
    }
}
