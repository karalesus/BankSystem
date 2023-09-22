module com.example.banksystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.example.banksystem to javafx.fxml;
    exports com.example.banksystem;
    exports  com.example.banksystem.Controllers;
    exports com.example.banksystem.Controllers.Admin;
    exports com.example.banksystem.Controllers.Client;
    exports com.example.banksystem.Controllers.Worker;
    exports com.example.banksystem.Models;
    exports com.example.banksystem.Views;
}