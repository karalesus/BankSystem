package com.example.banksystem.Controllers.Consultant;

import com.example.banksystem.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionBoardController implements Initializable {
    public Button info_button1;
    public Button info_button2;
    public Button info_button3;
    public Button info_button4;
    public Button info_button5;
    public Button info_button6;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        info_button1.setOnAction(event -> Model.getInstance().getViewFactory().showMessageWindow("AccessToBank"));
        info_button2.setOnAction(event -> Model.getInstance().getViewFactory().showMessageWindow("ForgotPassword"));
        info_button3.setOnAction(event -> Model.getInstance().getViewFactory().showMessageWindow("SavingsAccounts"));
        info_button4.setOnAction(event -> Model.getInstance().getViewFactory().showMessageWindow("InfoForWorkers"));
        info_button5.setOnAction(event -> Model.getInstance().getViewFactory().showMessageWindow("OpeningSavings"));
        info_button6.setOnAction(event -> Model.getInstance().getViewFactory().showMessageWindow("DepositMoney"));
    }
}
