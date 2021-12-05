package com.gestionnaire_de_stage.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EvalStagiaireDTO {

    private String companyName;

    private String phone;

    private String internName;

    private String programStudyField;

    private String questionStudentCanOrganizeWork;

    private String questionUnderstandDirectives;

    private String questionStudentMaintainRythmForWork;

    private String questionStudentHasTheRightPriorities;

    private String questionStudentRespectSchedule;

    private String questionStudentRespectTaskGiven;

    private String questionStudentPaysAttentionToDetails;

    private String questionStudentValidateHisWork;

    private String questionStudentTakeInitiativeToPerfectHimself;

    private String questionStudentAnalyzeProblemsGiven;

    private String questionStudentIsSocial;

    private String questionStudentWorksWellInGroup;

    private String questionStudentAdaptsToEasilyToWorkCulture;

    private String questionStudentAcceptConstructiveCriticism;

    private String questionStudentIsRespectful;

    private String questionStudentUnderstandOtherPeoplePov;

    private String questionStudentMotivatedToWork;

    private String questionStudentTellIdeasClearly;

    private String questionStudentHasInitiative;

    private String questionStudentWorkSecurely;

    private String questionStudentHasGoodSenseOfResponsability;

    private String questionStudentPonctual;

    private String programmeEtudes;

    private String appreciationGlobale;

    private boolean evaluationDiscuteAvecEtudiant;

    private String commentsProductivity;

    private String commentsQualityOfWorkIntern;

    private String commentsStudentSocialRelationsQuality;

    private String commentsStudentPersonalAptitudes;

    private String commentsInternProductivity;

    private boolean companyAppreciateStudent;

    private int nbHeuresReelTravailEtudiant;

    private String firstFonction;

    private String secondFunction;

    private String commentsStudentTraining;

    private String name;

    private LocalDate dateSignature = LocalDate.now();

    private String monitorSignature;

    private String studentEmail;
}