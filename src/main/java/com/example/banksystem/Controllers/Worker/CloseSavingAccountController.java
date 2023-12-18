package com.example.banksystem.Controllers.Worker;

import com.example.banksystem.Models.Client;
import com.example.banksystem.Models.Model;
import com.example.banksystem.Views.ClientCellFactory;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CloseSavingAccountController implements Initializable {

    public TextField pAddress_fld;
    public Button search_btn;
    public ListView<Client> result_listview;
    public TextField amount_fld;
    public Button deposit_btn;
    public Label error_lbl;
    public Label info_lbl;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search_btn.setOnAction(event -> onClientSearch());
        deposit_btn.setOnAction(event -> onDeposit());
    }

    private void onClientSearch() {
        ObservableList<Client> searchResults = Model.getInstance().searchClient(pAddress_fld.getText());
        result_listview.setItems(searchResults);
        result_listview.setCellFactory(e -> new ClientCellFactory());
        client = searchResults.get(0);
        if (client.savingsAccountProperty().get().accountNumberProperty().get() == null) {
            info_lbl.setText("Вклад уже закрыт");
            emptyFields();
        } else {
            info_lbl.setText(String.valueOf(client.savingsAccountProperty().get().balanceProperty().get()));
        }
    }

    private void onDeposit() {
        double amount = Double.parseDouble(amount_fld.getText());
        double newBalance = amount + client.checkingAccountProperty().get().balanceProperty().get();
        if (amount_fld.getText() != null && client.savingsAccountProperty().get().accountNumberProperty().get() != null) {
            if (Double.parseDouble(amount_fld.getText()) != client.savingsAccountProperty().get().balanceProperty().get()) {
                error_lbl.setStyle("-fx-text-fill: red; -fx-font-size: 1.3em; -fx-font-weight: bold");
                error_lbl.setText("Необходимо перевести в точности всю сумму, которая есть на вкладе");
            } else if ((Double.parseDouble(amount_fld.getText()) == client.savingsAccountProperty().get().balanceProperty().get())) {
                error_lbl.setText("");
                Model.getInstance().getDatabaseDriver().depositSavings(client.pAddressProperty().get(), newBalance);
                Model.getInstance().getDatabaseDriver().closeSavings(client.pAddressProperty().get());
                info_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold");
                info_lbl.setText("Деньги успешно переведены, вклад " + client.pAddressProperty().get() + " закрыт");
            }
        }
        emptyFields();
    }

    private void emptyFields() {
        pAddress_fld.setText("");
        amount_fld.setText("");
    }
}
