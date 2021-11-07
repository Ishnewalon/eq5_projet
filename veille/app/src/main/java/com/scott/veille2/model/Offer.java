package com.scott.veille2.model;

import java.time.LocalDate;

public class Offer {
    private long id;
    private String department;
    private String title;
    private String description;
    private LocalDate dateDebut;
    private double salary;

    public Offer(long id, String department, String title, String description, double salary) {
        this.id = id;
        this.department = department;
        this.title = title;
        this.description = description;
        this.dateDebut = dateDebut;
        this.salary = salary;
    }

    public long getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public double getSalary() {
        return salary;
    }
}
