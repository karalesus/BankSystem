package com.example.banksystem.Controllers.Client;

import com.example.banksystem.Models.Model;
import com.example.banksystem.Models.Transaction;
import com.example.banksystem.Views.TransactionCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView<Transaction> transaction_listview;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initAllTransactionsList();
        transaction_listview.setItems(Model.getInstance().getAllTransactions());
        transaction_listview.setCellFactory(e -> new TransactionCellFactory());
    }

    private void initAllTransactionsList() {
        if (Model.getInstance().getAllTransactions().isEmpty()){
            Model.getInstance().setAllTransactions();
        }
    }
}
