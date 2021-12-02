package com.gestionnaire_de_stage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gestionnaire_de_stage.listener.CurriculumListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@EntityListeners(CurriculumListener.class)
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Curriculum implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    private String type;

    @Lob
    private byte[] data;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private Student student;

    private Boolean isValid;

    public Curriculum(String name, String type, byte[] data, Student student) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.student = student;
        this.isValid = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Curriculum that = (Curriculum) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
