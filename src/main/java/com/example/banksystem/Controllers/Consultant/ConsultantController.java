package com.example.banksystem.Controllers.Consultant;

import com.example.banksystem.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ConsultantController implements Initializable {
    public BorderPane consultant_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getConsultantSelectedMenuItem().addListener((observableValue, s, newVal) -> {
            switch (newVal) {
                default -> consultant_parent.setCenter(Model.getInstance().getViewFactory().getQuestionBoardView());
            }
        });
    }
}

