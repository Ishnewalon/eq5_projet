package com.gestionnaire_de_stage.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String token;
    @ManyToOne
    private User user;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created;
    private boolean unusable;//TODO maybe un puller?

    public PasswordResetToken(User user) {
        this.user = user;
        created = new Date();
        token = UUID.randomUUID().toString();
        unusable = false;
    }


    public void setUnusable() {
        this.unusable = true;
    }
}
