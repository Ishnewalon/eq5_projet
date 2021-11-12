package com.gestionnaire_de_stage.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EvalMilieuStageDTO {

    String entrepriseNom;

    String personneContact;

    String phone;

    String telecopieur;

    String adresse;

    String zip;

    String ville;

    String nomStagiaire;

    String dateStage;

    int stageCourant;

    String questionUn;

    String questionDeux;

    String questionTrois;

    int nbHeuresMoisUn;

    int nbHeuresMoisDeux;

    int nbHeuresMoisTrois;

    String questionQuatre;

    String questionCinq;

    String questionSix;

    String questionSept;

    float salaireStagiaire;

    String questionHuit;

    String questionNeuf;

    String questionDix;

    String commentaires;

    String questionOnze;

    String questionDouze;

    String questionTreize;

    String questionQuatorzeHeuresUnA;

    String questionQuatorzeHeuresUnB;

    String questionQuatorzeHeuresUnC;

    String questionQuatorzeHeuresUnD;

    String questionQuatorzeHeuresUnE;

    String questionQuatorzeHeuresUnF;

    String questionQuinze;

    String signatureSuperviseur;

    String matriculeEtudiant;

    LocalDate signatureDate;
}
