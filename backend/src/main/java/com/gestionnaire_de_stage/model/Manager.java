package com.gestionnaire_de_stage.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@ToString(callSuper = true)
public class Manager extends User {

}