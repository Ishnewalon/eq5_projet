package com.gestionnaire_de_stage.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User targetedUser;

    private boolean seen;

    private String message;

    private LocalDateTime createdDate;

    public Notification() {
        this.seen = false;
        this.createdDate = LocalDateTime.now();
    }

    public Notification(User targetedUser, String message) {
        this.targetedUser = targetedUser;
        this.message = message;
        this.seen = false;
        this.createdDate = LocalDateTime.now();
    }
}
