package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.List;

public interface OfferApplicationRepository extends JpaRepository<OfferApplication, Long> {
    boolean existsByOfferAndCurriculum(Offer offer, Curriculum curriculum);

    List<OfferApplication> getAllByOffer_CreatorEmail(String email);

    List<OfferApplication> getAllByStatus(Status status);
    List<OfferApplication> getAllByStatusAndCurriculum_StudentId(Status status, Long id);
    List<OfferApplication> findAllByCurriculum_Student_Supervisor_Id(Long id);
    List<OfferApplication> getAllByCurriculum_StudentId(Long id);
    List<OfferApplication> getAllByStatusAndSession_YearGreaterThanEqual(Status status, Year session_year);
    List<OfferApplication> getAllByStatusAndCurriculum_StudentIdAndSession_YearGreaterThanEqual(Status status, Long curriculum_student_id, Year session_year);
    List<OfferApplication> findAllByCurriculum_Student_Supervisor_IdAndSession_YearGreaterThanEqual(Long curriculum_student_supervisor_id, Year session_year);
}
