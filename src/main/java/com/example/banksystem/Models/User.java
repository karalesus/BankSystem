package com.example.banksystem.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty role;

    public User(String fName, String lName, String username, String password, String role) {
        this.firstName = new SimpleStringProperty(this, "First Name", fName);
        this.lastName = new SimpleStringProperty(this, "Last Name", lName);
        this.username = new SimpleStringProperty(this, "Username", username);
        this.password = new SimpleStringProperty(this, "Password", password);
        this.role = new SimpleStringProperty(this, "Role", role);
    }


    public StringProperty firstNameProperty() {
        return firstName;
    }


    public StringProperty lastNameProperty() {
        return lastName;
    }


    public StringProperty usernameProperty() {
        return username;
    }


    public StringProperty roleProperty() {
        return role;
    }
}
