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
    private String token = UUID.randomUUID().toString();
    @ManyToOne
    private User user;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created = new Date();
    private boolean unusable;//TODO maybe un puller?

    public PasswordResetToken(User user) {
        this.user = user;
    }


    public void setUnusable() {
        this.unusable = true;
    }
}
