package com.example.banksystem.Models;

import com.example.banksystem.Views.AccountType;
import com.example.banksystem.Views.ViewFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;


    // Client Data Section
    private final Client client;
    private boolean clientLoginSuccessFlag;

    // Worker Data Section
    private boolean workerLoginSuccessFlag;
    // Admin Data Section

    private Model() {
        this.databaseDriver = new DatabaseDriver();
        this.viewFactory = new ViewFactory();

        // Client Data Section
        this.clientLoginSuccessFlag = false;
        this.client = new Client("", "", "", null, null, null);
        // Worker Data Section
        // Admin Data Section
        this.workerLoginSuccessFlag = false;
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    /*
     * Client Method Section
     */

    public boolean getClientLoginSuccessFlag() {
        return this.clientLoginSuccessFlag;
    }

    public void setClientLoginSuccessFlag(boolean flag) {
        this.clientLoginSuccessFlag = flag;
    }

    public Client getClient() {
        return client;
    }


    public void evaluateClientCred(String pAddress, String password) {
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet resultSet = databaseDriver.getClientData(pAddress, password);
        try {
            if (resultSet.isBeforeFirst()) {
                this.client.firstNameProperty().set(resultSet.getString("FirstName"));
                this.client.lastNameProperty().set(resultSet.getString("LastName"));
                this.client.pAddressProperty().set(resultSet.getString("PayeeAddress"));
                String[] dateParts = resultSet.getString("Date").split("\\.");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[0]));
                this.client.dateProperty().set(date);
                this.clientLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Worker Method Section
     */
    public boolean getWorkerLoginSuccessFlag() {
        return workerLoginSuccessFlag;
    }

    public void setWorkerLoginSuccessFlag(boolean workerLoginSuccessFlag) {
        this.workerLoginSuccessFlag = workerLoginSuccessFlag;
    }

    public void evaluateWorkerCred(String username, String password) {
        ResultSet resultSet = getDatabaseDriver().getWorkerData(username, password);
        try {
            if (resultSet.isBeforeFirst()) {
                this.workerLoginSuccessFlag = true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
