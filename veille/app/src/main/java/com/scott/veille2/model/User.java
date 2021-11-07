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

    public long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
