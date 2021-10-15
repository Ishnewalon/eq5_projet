package com.gestionnaire_de_stage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(callSuper = true)
public class Manager extends User {
}