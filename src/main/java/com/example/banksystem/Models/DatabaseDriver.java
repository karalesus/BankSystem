package com.example.banksystem.Models;

import java.sql.*;

public class DatabaseDriver {
    private Connection conn;

    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:BankSystem.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Client Section
     */

    public ResultSet getClientData(String pAddress, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress = '" + pAddress + "' AND Password='" + password + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /*
     * Worker Section
     */

    public ResultSet getWorkerData(String username, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Workers WHERE Username='" + username + "' AND Password='" + password + "';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /*
     * Worker Method Section
     */

    /*
     * Admin Section
     */

    /*
     * Utility Methods
     */
}
