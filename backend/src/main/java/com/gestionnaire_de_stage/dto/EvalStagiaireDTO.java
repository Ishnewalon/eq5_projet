package com.gestionnaire_de_stage.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EvalStagiaireDTO {

    String companyName;

    String phone;

    String internName;

    String programStudyField;

    String questionStudentCanOrganizeWork;

    String questionUnderstandDirectives;

    String questionStudentMaintainRythmForWork;

    String questionStudentHasTheRightPriorities;

    String questionStudentRespectSchedule;

    String questionStudentRespectTaskGiven;

    String questionStudentPaysAttentionToDetails;

    String questionStudentValidateHisWork;

    String questionStudentTakeInitiativeToPerfectHimself;

    String questionStudentAnalyzeProblemsGiven;

    String questionStudentIsSocial;

    String questionStudentWorksWellInGroup;

    String questionStudentAdaptsToEasilyToWorkCulture;

    String questionStudentAcceptConstructiveCriticism;

    String questionStudentIsRespectful;

    String questionStudentUnderstandOtherPeoplePov;

    String questionStudentMotivatedToWork;

    String questionStudentTellIdeasClearly;

    String questionStudentHasInitiative;

    String questionStudentWorkSecurely;

    String questionStudentHasGoodSenseOfResponsability;

    String questionStudentPonctual;

    String programmeEtudes;

    String appreciationGlobale;

    boolean evaluationDiscuteAvecEtudiant;

    String commentsProductivity;

    String commentsQualityOfWorkIntern;

    String commentsStudentSocialRelationsQuality;

    String commentsStudentPersonalAptitudes;

    String commentsInternProductivity;

    boolean companyAppreciateStudent;

    int nbHeuresReelTravailEtudiant;

    String firstFonction;

    String secondFunction;

    String commentsStudentTraining;

    String name;

    LocalDate dateSignature = LocalDate.now();

    String monitorSignature;

    String studentEmail;
}