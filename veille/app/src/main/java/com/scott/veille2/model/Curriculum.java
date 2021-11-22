package com.scott.veille2.model;

public class Curriculum {
    private Long id;

    private String name;

    private String type;

    private Boolean isValid;

    public Curriculum(Long id, String name, String type, Boolean isValid) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isValid = isValid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
}
