package com.gestionnaire_de_stage.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User targetedUser;

    private boolean seen;

    private String message;

    private LocalDateTime createdDate = LocalDateTime.now();

    public Notification(User targetedUser, String message) {
        this.targetedUser = targetedUser;
        this.message = message;
    }
}
