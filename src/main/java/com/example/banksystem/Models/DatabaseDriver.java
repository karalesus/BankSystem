package com.example.banksystem.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDate;

import static com.example.banksystem.Views.RoleType.СЛУЖАЩИЙ;

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

    public ResultSet getTransactions(String pAddress, int limit) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Transactions WHERE Sender= '" + pAddress + "'OR Receiver='" + pAddress + "'LIMIT'" + limit + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // возвращает баланс карты
    public double getCheckingAccountBalance(String pAddress) {
        Statement statement;
        ResultSet resultSet;
        double balance = 0;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE Owner ='" + pAddress + "';");
            balance = resultSet.getDouble("Balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }


    // увеличение/уменьшение баланса.
    public void updateBalance(String pAddress, double amount, String operation) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE Owner= '" + pAddress + "';");
            double newBalance;
            if (operation.equals("ADD")) {
                newBalance = resultSet.getDouble("Balance") + amount;
                statement.executeUpdate("UPDATE CheckingAccounts SET Balance = '" + newBalance + "'WHERE Owner='" + pAddress + "';");
            } else {
                if (resultSet.getDouble("Balance") >= amount) {
                    newBalance = resultSet.getDouble("Balance") - amount;
                    statement.executeUpdate("UPDATE CheckingAccounts SET Balance ='" + newBalance + "'WHERE Owner='" + pAddress + "';");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getCheckingAccountBal(String accountNumber) {
        Statement statement;
        ResultSet checkingResultSet;
        double checkingBalance = 0;
        try {
            statement = this.conn.createStatement();
            checkingResultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE AccountNumber ='" + accountNumber + "';");
            checkingBalance = checkingResultSet.getDouble("Balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checkingBalance;
    }

    public double getSavingsAccountBal(String accountNumber) {
        Statement statement;
        ResultSet savingResultSet;
        double savingsBalance = 0;
        try {
            statement = this.conn.createStatement();
            savingResultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE AccountNumber ='" + accountNumber + "';");
            savingsBalance = savingResultSet.getDouble("Balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return savingsBalance;
    }

    public String getAccountNumber(String owner) {
        Statement statement;
        ResultSet resultSet;
        String accountNumber = "";
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner ='" + owner + "';");
            accountNumber = resultSet.getString("AccountNumber");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountNumber;
    }

    public void createSavingsUpdateBalance(String chAccNum, double amount, String operation) {
        Statement statement;
        ResultSet checkingResultSet;
        double checkingBalance;
        try {
            statement = this.conn.createStatement();
            checkingResultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE AccountNumber= '" + chAccNum + "';");
            if (operation.equals("OPEN_SAVINGS")) {
                checkingBalance = (checkingResultSet.getDouble("Balance") - amount);
                statement.executeUpdate("UPDATE CheckingAccounts SET Balance = '" + checkingBalance + "'WHERE AccountNumber='" + chAccNum + "';");
            } else {
                checkingBalance = checkingResultSet.getDouble("Balance") + amount;
                statement.executeUpdate("UPDATE CheckingAccounts SET Balance = '" + checkingBalance + "'WHERE AccountNumber='" + chAccNum + "';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateCheckingAccountBalance(String chAccNum, String svAccNum, double amount, String operation) {
        Statement statement;
        ResultSet checkingResultSet;
        ResultSet savingsResultSet;
        double checkingBalance;
        double savingsBalance;
        try {
            statement = this.conn.createStatement();
            checkingResultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE AccountNumber= '" + chAccNum + "';");
            savingsResultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE AccountNumber= '" + svAccNum + "';");
            if (operation.equals("AMOUNT_TO_SV")) {
//            resultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE AccountNumber= '" + accountNumber + "';");
                savingsBalance = (savingsResultSet.getDouble("Balance") + amount);
                checkingBalance = (checkingResultSet.getDouble("Balance") - amount);
                statement.executeUpdate("UPDATE SavingsAccounts SET Balance = '" + savingsBalance + "'WHERE AccountNumber='" + svAccNum + "';");
                statement.executeUpdate("UPDATE CheckingAccounts SET Balance = '" + checkingBalance + "'WHERE AccountNumber='" + chAccNum + "';");
            } else {
                checkingBalance = checkingResultSet.getDouble("Balance") + amount;
                savingsBalance = savingsResultSet.getDouble("Balance") - amount;
                statement.executeUpdate("UPDATE CheckingAccounts SET Balance = '" + checkingBalance + "'WHERE AccountNumber='" + chAccNum + "';");
                statement.executeUpdate("UPDATE SavingsAccounts SET Balance = '" + savingsBalance + "'WHERE AccountNumber='" + svAccNum + "';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // создание и запись новой транзакции
    public void newTransaction(String sender, String receiver, double amount, String message) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            LocalDate date = LocalDate.now();
            statement.executeUpdate("INSERT INTO "+
                    "Transactions (Sender,Receiver, Amount, Date, Message)" +
                    "VALUES ('" + sender + "', '" + receiver + "', '" + amount + "', '" + date + "', '" + message + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeSavingAccount(String accountNumber) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("DELETE FROM SavingsAccounts WHERE AccountNumber= '" + accountNumber + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closingDeposit(String chAccNum, String svAccNum, double amount) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE CheckingAccounts SET Balance = '" + amount + "'WHERE AccountNumber='" + svAccNum + "';");
            statement.executeUpdate("UPDATE SavingsAccounts SET Balance = '" + 0 + "'WHERE AccountNumber='" + chAccNum + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void createClient(String fName, String lName, String pAddress, String password, LocalDate date) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Clients (FirstName, LastName, PayeeAddress, Password, Date)" +
                    "VALUES ('" + fName + "','" + lName + "','" + pAddress + "','" + password + "','" + date.toString() + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCheckingAccount(String owner, String number, double tLimit, double balance) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "CheckingAccounts (Owner, AccountNumber, TransactionLimit, Balance)" +
                    "VALUES ('" + owner + "','" + number + "','" + tLimit + "','" + balance + "');");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createSavingsAccount(String owner, String number, double wLimit, double balance) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "SavingsAccounts (Owner, AccountNumber, WithdrawalLimit, Balance)" +
                    "VALUES ('" + owner + "','" + number + "','" + wLimit + "','" + balance + "');");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAllClientsData() {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public void deleteClient(String pAddress) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("DELETE FROM Clients WHERE PayeeAddress= '" + pAddress + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void depositSavings(String pAddress, double amount) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE CheckingAccounts SET Balance = '" + amount + "'WHERE Owner='" + pAddress + "';");
            statement.executeUpdate("UPDATE SavingsAccounts SET Balance = '" + 0 + "'WHERE Owner='" + pAddress + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeSavings(String pAddress) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("DELETE FROM SavingsAccounts WHERE Owner= '" + pAddress + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Admin Section
     */

    /*
     * Utility Methods
     */

    public ResultSet searchClient(String pAddress) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress='" + pAddress + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public int getLastClientsID() {
        Statement statement;
        ResultSet resultSet;
        int id = 0;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name='Clients';");
            id = resultSet.getInt("seq");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public ResultSet getCheckingAccountData(String pAddress) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE Owner='" + pAddress + "';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getSavingsAccountData(String pAddress) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner='" + pAddress + "';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    /*
    Admin methods section
     */
    public int getLastUserID() {
        Statement statement;
        ResultSet resultSet;
        int id = 0;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name='Users';");
            id = resultSet.getInt("seq");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public ResultSet getAdminData(String username, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Admins WHERE Username='" + username + "' AND Password='" + password + "';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void createUser(String fName, String lName, String username, String password, String role) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Users (FirstName, LastName, Username, Password, Role)" +
                    "VALUES ('" + fName + "','" + lName + "','" + username + "','" + password + "','" + role + "');");
            if (role.equals("СЛУЖАЩИЙ")) {
                insertWorker(fName, lName, username, password);
            }
            if (role.equals("КОНСУЛЬТАНТ")) {
                insertConsultant(fName, lName, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String username) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("DELETE FROM Users WHERE Username= '" + username + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllUsersData() {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Users;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public void insertWorker(String fName, String lName, String username, String password) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Workers (FirstName, LastName, Username, Password)" +
                    "VALUES ('" + fName + "','" + lName + "','" + username + "','" + password + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertConsultant(String fName, String lName, String username, String password) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Consultants (FirstName, LastName, Username, Password)" +
                    "VALUES ('" + fName + "','" + lName + "','" + username + "','" + password + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
    Consultant Section
     */
    public ResultSet getConsultantData(String username, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Consultants WHERE Username='" + username + "' AND Password='" + password + "';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
