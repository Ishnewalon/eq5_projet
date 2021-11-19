package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> getAllByManagerSignatureNull();

    Contract getContractByIdAndMonitorSignatureNotNullAndManagerSignatureNotNullAndStudentSignatureNull(Long id);

    List<Contract> getAllByOffer_CreatorIdAndMonitorSignatureNullAndManagerSignatureNotNullAndSession_YearGreaterThanEqual(Long offer_creator_id, Year year);

    Contract getContractByIdAndManagerSignatureNotNullAndMonitorSignatureNullAndStudentSignatureNull(Long id);

    Contract getContractByIdAndManagerSignatureNullAndMonitorSignatureNullAndStudentSignatureNullAndSession_YearGreaterThanEqual(Long id, Year year);

    Contract getByStudent_IdAndManagerSignatureNotNullAndMonitorSignatureNotNullAndStudentSignatureNullAndSession_YearGreaterThanEqual(Long student_id, Year session_year);

    Contract getContractByStudent_Matricule(String matricule);

    boolean existsByStudentId(Long id);

    boolean existsByStudentMatricule(String matricule);

    List<Contract> getAllByManager_IdAndManagerSignatureNotNullAndSession_YearGreaterThanEqual(Long manager_id, Year year);

    List<Contract> getAllByMonitor_IdAndManagerSignatureNotNullAndMonitorSignatureNotNullAndSession_YearGreaterThanEqual(Long monitor_id, Year year);

    boolean existsByStudentIdAndSession(Long idStudent, Session session);

    Contract getByStudent_IdAndManagerSignatureNotNullAndMonitorSignatureNotNullAndStudentSignatureNotNullAndSession_YearGreaterThanEqual(Long student_id, Year year);

    boolean existsByIdAndSession_YearGreaterThanEqual(Long id, Year year);
}
