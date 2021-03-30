package com.cybarz.driverapp;

import java.security.PublicKey;

public class User {
    public String username;
    public String Number;



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String number) {
        this.username = username;
        this.Number = number;
    }
}
