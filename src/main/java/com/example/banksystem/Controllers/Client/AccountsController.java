package com.example.banksystem.Controllers.Client;

import com.example.banksystem.Models.Client;
import com.example.banksystem.Models.Model;
import com.example.banksystem.Views.AccountType;
import com.example.banksystem.Views.RoleType;
import com.example.banksystem.Views.SavingType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    public Label ch_acc_num;
    public Label ch_acc_date;
    public Label ch_acc_bal;
    public Label sv_acc_num;
    public Label withdrawal_limit;
    public Label sv_acc_date;
    public Label sv_acc_bal;
    public Label info_lbl;
    public Label info_lbl2;
    public TextField amount_to_sv;
    public Button trans_to_sv_btn;
    public TextField amount_to_ch;
    public Button trans_to_ch_btn;
    public Button close_saving_account_btn;
    public Button open_saving_account_btn;
    public Label done_lbl;
    public ChoiceBox<SavingType> saving_account_selector;
    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        trans_to_sv_btn.setOnAction(event -> onSendMoneyToSavingAccount());
        trans_to_ch_btn.setOnAction(event -> onSendMoneyToCheckingAccount());
        saving_account_selector.setItems(FXCollections.observableArrayList(SavingType.ЛУЧШИЙ50000, SavingType.ВЫГОДНЫЙ100000, SavingType.МОЛОДЕЖНЫЙ20000));
        saving_account_selector.setValue(Model.getInstance().getViewFactory().getSavingAccountType());
        open_saving_account_btn.setOnAction(event -> onCreateAccountNumber());
        close_saving_account_btn.setOnAction(event -> Model.getInstance().getViewFactory().showAcceptWindow(ch_acc_num.textProperty().get()));
        close_saving_account_btn.setOnAction(event -> onCloseSavingAccount());
    }


    private void bindData() {
        ch_acc_bal.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().asString());
        ch_acc_num.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().accountNumberProperty());
        sv_acc_bal.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().asString());
        sv_acc_num.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().accountNumberProperty());
    }

    private void onSendMoneyToSavingAccount() {
        String chAccNum = ch_acc_num.textProperty().get();
        String svAccNum = sv_acc_num.textProperty().get();
        String chAccBal = ch_acc_bal.textProperty().get();
        double amount = Double.parseDouble(amount_to_sv.getText());
        try {
            if (Double.parseDouble(chAccBal) >= amount) {
                Model.getInstance().getDatabaseDriver().updateCheckingAccountBalance(chAccNum, svAccNum, amount, "AMOUNT_TO_SV");
                info_lbl.setText("Деньги успешно отправлены!");
                info_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold");
            } else {
                info_lbl.setText("Денег на карте недостаточно!");
                info_lbl.setStyle("-fx-text-fill: red; -fx-font-size: 1.3em; -fx-font-weight: bold");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // обновляем счет вклада в клиентом дашборде сразу
        Model.getInstance().getClient().checkingAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getCheckingAccountBal(chAccNum));
        Model.getInstance().getClient().savingsAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getSavingsAccountBal(svAccNum));
        amount_to_sv.setText("");
    }

    private void onSendMoneyToCheckingAccount() {
        String chAccNum = ch_acc_num.textProperty().get();
        String svAccNum = sv_acc_num.textProperty().get();
        String svAccBal = sv_acc_bal.textProperty().get();
        double amount = Double.parseDouble(amount_to_ch.getText());
        try {
            if (Double.parseDouble(svAccBal) >= amount) {
                Model.getInstance().getDatabaseDriver().updateCheckingAccountBalance(chAccNum, svAccNum, amount, "AMOUNT_TO_CH");
                info_lbl.setText("Деньги успешно отправлены!");
                info_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold");
            } else {
                info_lbl.setText("Денег на вкладе недостаточно!");
                info_lbl.setStyle("-fx-text-fill: red; -fx-font-size: 1.3em; -fx-font-weight: bold");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Model.getInstance().getClient().checkingAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getCheckingAccountBal(chAccNum));
        Model.getInstance().getClient().savingsAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getSavingsAccountBal(svAccNum));
        amount_to_ch.setText("");
    }

    private void onCloseSavingAccount() {
        String chAccNum = ch_acc_num.textProperty().get();
        String svAccNum = sv_acc_num.textProperty().get();
        double amount = Double.parseDouble(sv_acc_bal.textProperty().get());
        if (svAccNum != null) {
            Model.getInstance().getDatabaseDriver().closingDeposit(chAccNum, svAccNum, amount);
            Model.getInstance().getDatabaseDriver().closeSavingAccount(svAccNum);
            done_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.0em; -fx-font-weight: bold");
            Model.getInstance().getDatabaseDriver().createSavingsUpdateBalance(chAccNum, amount, "CLOSE_SAVINGS");
            done_lbl.setText("Вклад успешно закрыт. Деньги переведены на карту");
        } else {
            done_lbl.setStyle("-fx-text-fill: red; -fx-font-size: 1.3em; -fx-font-weight: bold");
            done_lbl.setText("У вас нет вклада!");
        }
        Model.getInstance().getClient().checkingAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getCheckingAccountBal(chAccNum));
        Model.getInstance().getClient().savingsAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getSavingsAccountBal(svAccNum));
        Model.getInstance().getClient().savingsAccountProperty().get().setAccountNumber(null);
    }

    private void onCreateAccountNumber() {
        String chAccBal = ch_acc_bal.textProperty().get();
        String chAccNum = ch_acc_num.textProperty().get();
        Model.getInstance().getViewFactory().setSavingAccountType(saving_account_selector.getValue());
        String owner = Model.getInstance().getClient().pAddressProperty().get();
        // Generate Account Number
        String firstSection = "3201";
        String lastSection = Integer.toString((new Random().nextInt(9999) + 1000));
        String accountNumber = firstSection + " " + lastSection;
        if (Model.getInstance().getClient().savingsAccountProperty().get().accountNumberProperty().get() == null) {
            if (Model.getInstance().getViewFactory().getSavingAccountType() == SavingType.ЛУЧШИЙ50000) {
                if (Double.parseDouble(chAccBal) >= 50000) {
                    Model.getInstance().getDatabaseDriver().createSavingsAccount(owner, accountNumber, 10000, 50000);
                    Model.getInstance().getDatabaseDriver().createSavingsUpdateBalance(chAccNum, 50000, "OPEN_SAVINGS");
                    done_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.0em; -fx-font-weight: bold");
                    done_lbl.setText("Вклад успешно создан");
                } else {
                    done_lbl.setStyle("-fx-text-fill: red; -fx-font-size: 1.0em; -fx-font-weight: bold");
                    done_lbl.setText("Недостаточно денег на карте");
                }
            }

            if (Model.getInstance().getViewFactory().getSavingAccountType() == SavingType.ВЫГОДНЫЙ100000) {
                if (Double.parseDouble(chAccBal) >= 100000) {
                    Model.getInstance().getDatabaseDriver().createSavingsAccount(owner, accountNumber, 10000, 100000);
                    Model.getInstance().getDatabaseDriver().createSavingsUpdateBalance(chAccNum, 100000, "OPEN_SAVINGS");
                    done_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold");
                    done_lbl.setText("Вклад успешно создан");
                } else {
                    done_lbl.setStyle("-fx-text-fill: red; -fx-font-size: 1.0em; -fx-font-weight: bold");
                    done_lbl.setText("Недостаточно денег на карте");
                }
            }

                if (Model.getInstance().getViewFactory().getSavingAccountType() == SavingType.МОЛОДЕЖНЫЙ20000) {
                    if (Double.parseDouble(chAccBal) >= 20000) {
                        Model.getInstance().getDatabaseDriver().createSavingsAccount(owner, accountNumber, 10000, 20000);
                        Model.getInstance().getDatabaseDriver().createSavingsUpdateBalance(chAccNum, 10000, "OPEN_SAVINGS");
                        done_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold");
                        done_lbl.setText("Вклад успешно создан");
                    } else {
                        done_lbl.setStyle("-fx-text-fill: red; -fx-font-size: 1.0em; -fx-font-weight: bold");
                        done_lbl.setText("Недостаточно денег на карте");
                    }
                }
                } else {
                    done_lbl.setStyle("-fx-text-fill: red; -fx-font-size: 1.0em; -fx-font-weight: bold");
                    done_lbl.setText("У вас уже есть вклад");
                }
        Model.getInstance().getClient().savingsAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getSavingsAccountBal(accountNumber));
        Model.getInstance().getClient().checkingAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getCheckingAccountBal(chAccNum));
        Model.getInstance().getClient().savingsAccountProperty().get().setAccountNumber(Model.getInstance().getDatabaseDriver().getAccountNumber(owner));
    }
}



