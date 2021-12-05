package com.gestionnaire_de_stage.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EvalMilieuStageDTO {

    private String companyName;

    private String contactPerson;

    private String phone;

    private String fax;

    private String adresse;

    private String zip;

    private String city;

    private String internName;

    private String dateStage;

    private int currentInternship;

    private String questionTaskGivenWereInContract;

    private String questionInternshipEasyToIntegrateInterns;

    private String questionWasEnoughTimeToTrainIntern;

    private int nbHoursMonthOne;

    private int nbHoursMonthTwo;

    private int nbHoursMonthThree;

    private String questionHygiene;

    private String questionWorkEnvironmentNice;

    private String questionWorkAccesibleByMetro;

    private String questionSalaryInterestingForIntern;

    private float salaryIntern;

    private String questionSupervisorFacilitateInternship;

    private String questionTaskGivenWereAdequate;

    private String questionWorkAcceptable;

    private String comments;

    private String questionPreferForInternship;

    private String questionWelcomeMoreThanTwoIntern;

    private String questionWelcomeSameIntern;

    private String mondayShiftStart;

    private String mondayShiftEnd;

    private String tuesdayShiftStart;

    private String tuesdayShiftEnd;

    private String wednesdayShiftStart;

    private String wednesdayShiftEnd;

    private String thursdayShiftStart;

    private String thursdayShiftEnd;

    private String fridayShiftStart;

    private String fridayShiftEnd;

    private String saturdayShiftStart;

    private String saturdayShiftEnd;

    private String sundayShiftStart;

    private String sundayShiftEnd;

    private String questionShiftsFlexible;

    private String supervisorSignature;

    private String studentMatricule;

    private LocalDate signatureDate;
}
