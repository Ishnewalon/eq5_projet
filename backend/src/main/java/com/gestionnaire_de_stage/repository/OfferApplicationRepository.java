package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.Year;
import java.util.List;

public interface OfferApplicationRepository extends JpaRepository<OfferApplication, Long> {
    boolean existsByOfferAndCurriculum(Offer offer, Curriculum curriculum);

    boolean existsByCurriculum(Curriculum curriculum);

    List<OfferApplication> getAllByOffer_CreatorEmail(String email);

    List<OfferApplication> getAllByCurriculum_StudentId(Long id);

    List<OfferApplication> getAllByStatusAndSession_YearGreaterThanEqual(Status status, Year session_year);

    List<OfferApplication> getAllByStatusAndCurriculum_StudentIdAndSession_YearGreaterThanEqual(Status status, Long curriculum_student_id, Year session_year);

    List<OfferApplication> findAllByCurriculum_Student_Supervisor_IdAndSession_YearGreaterThanEqual(Long curriculum_student_supervisor_id, Year session_year);

    @Query("UPDATE OfferApplication t SET t.status = ?2 WHERE t.status = ?1 and t.interviewDate < current_timestamp and t.session in (select s.id from Session s where s.year >= ?3)")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    int updateAllOfferApplicationThatWereInAInterviewStatusToStatus(Status oldStatus, Status newStatus, Year session_year);
}