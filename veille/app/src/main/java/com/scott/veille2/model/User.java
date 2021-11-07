package com.scott.veille2.model;

public class User {
    private long id;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;

    public User(long id, String lastName, String firstName, String email, String phone) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
    }
}
