package com.gestionnaire_de_stage.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EvalMilieuStageDTO {

    String companyName;

    String contactPerson;

    String phone;

    String fax;

    String adresse;

    String zip;

    String city;

    String internName;

    String dateStage;

    int currentInternship;

    String questionTaskGivenWereInContract;

    String questionInternshipEasyToIntegrateInterns;

    String questionWasEnoughTimeToTrainIntern;

    int nbHoursMonthOne;

    int nbHoursMonthTwo;

    int nbHoursMonthThree;

    String questionHygiene;

    String questionWorkEnvironmentNice;

    String questionWorkAccesibleByMetro;

    String questionSalaryInterestingForIntern;

    float salaryIntern;

    String questionSupervisorFacilitateInternship;

    String questionTaskGivenWereAdequate;

    String questionWorkAcceptable;

    String comments;

    String questionPreferForInternship;

    String questionWelcomeMoreThanTwoIntern;

    String questionWelcomeSameIntern;

    String mondayShiftStart;

    String mondayShiftEnd;

    String tuesdayShiftStart;

    String tuesdayShiftEnd;

    String wednesdayShiftStart;

    String wednesdayShiftEnd;

    String thursdayShiftStart;

    String thursdayShiftEnd;

    String fridayShiftStart;

    String fridayShiftEnd;

    String saturdayShiftStart;

    String saturdayShiftEnd;

    String sundayShiftStart;

    String sundayShiftEnd;

    String questionShiftsFlexible;

    String supervisorSignature;

    String studentMatricule;

    LocalDate signatureDate;
}
