package com.example.banksystem.Controllers.Client;

import com.example.banksystem.Models.Model;
import com.example.banksystem.Models.Transaction;
import com.example.banksystem.Views.TransactionCellFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Label login_date;
    public Text user_name;
    public Label checking_bal;
    public Label checking_acc_num;
    public Label saving_bal;
    public Label saving_acc_num;
    public Label income_lbl;
    public Label expense_lbl;
    public ListView<Transaction> transaction_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;
    public Label info_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        initTransactionsList();
        transaction_listview.setItems(Model.getInstance().getLatestTransactions());
        transaction_listview.setCellFactory(e -> new TransactionCellFactory());
        send_money_btn.setOnAction(event -> onSendMoney());
        accountSummary();
    }

    private void bindData() {
        user_name.textProperty().bind(Bindings.concat("Привет, ").concat(Model.getInstance().getClient().firstNameProperty()));
        login_date.setText("Сегодня " + LocalDate.now());
        checking_bal.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().asString());
        checking_acc_num.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().accountNumberProperty());
        saving_bal.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().asString());
        saving_acc_num.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().accountNumberProperty());
    }

    // инициализириуем лист транзакций один раз, чтобы каждый раз не делать это по новой
    private void initTransactionsList() {
        if (Model.getInstance().getLatestTransactions().isEmpty()) {
            Model.getInstance().setLatestTransactions();
        }
    }

    private void onSendMoney() {
        String receiver = payee_fld.getText();
        double amount = Double.parseDouble(amount_fld.getText());
        String message = message_fld.getText();
        String sender = Model.getInstance().getClient().pAddressProperty().get();
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().searchClient(receiver);
        try {
            if (resultSet.isBeforeFirst()) {
                Model.getInstance().getDatabaseDriver().updateBalance(receiver, amount, "ADD");
                info_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.0em; -fx-font-weight: bold");
                info_lbl.setText("Деньги успешно отправлены!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // уменьшение баланса вклада отправителя
        Model.getInstance().getDatabaseDriver().updateBalance(sender, amount, "REMOVE");
        // обновляем счет вклада в клиентом дашборде сразу
        Model.getInstance().getClient().checkingAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getCheckingAccountBalance(sender));
        Model.getInstance().getDatabaseDriver().newTransaction(sender, receiver, amount, message);
        payee_fld.setText("");
        amount_fld.setText("");
        message_fld.setText("");
    }

    // Метод, который считает доходы и расходы
    private void accountSummary() {
        double income = 0;
        double expenses = 0;
        // инициализируем ВСЕ транзакции
        if (Model.getInstance().getAllTransactions().isEmpty()) {
            Model.getInstance().setAllTransactions();
        }
        // в листе транзакций смотрим,
        // кто отправитель денег. если это тот, кто сейчас в аккаунте,
        // то мы считаем это за расход (иначе - доход)
        for (Transaction transaction : Model.getInstance().getAllTransactions()) {
            if (transaction.senderProperty().get().equals(Model.getInstance().getClient().pAddressProperty().get())) {
                expenses = expenses + transaction.amountProperty().get();
            } else {
                income = income + transaction.amountProperty().get();
            }
        }
        income_lbl.setText("+ " + income + " ₽");
        expense_lbl.setText("- " + expenses + " ₽");
    }
}
