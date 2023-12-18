package com.example.banksystem.Models;

import javafx.beans.property.StringProperty;

public class Admin {

    private final StringProperty username;
    private final StringProperty password;

    public Admin(StringProperty username, StringProperty password) {
        this.username = username;
        this.password = password;
    }
}
