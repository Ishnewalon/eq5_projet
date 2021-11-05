package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> getAllByManagerSignatureNull();

    Contract getContractByIdAndMonitorSignatureNotNullAndManagerSignatureNotNullAndStudentSignatureNull(Long id);

    List<Contract> getAllByOffer_CreatorIdAndMonitorSignatureNullAndManagerSignatureNotNull(Long id);

    Contract getContractByIdAndManagerSignatureNotNullAndMonitorSignatureNullAndStudentSignatureNull(Long id);

    Contract getContractByIdAndManagerSignatureNullAndMonitorSignatureNullAndStudentSignatureNull(Long contract_id);

    Contract getContractByStudent_IdAndManagerSignatureNotNullAndMonitorSignatureNotNullAndStudentSignatureNull(Long student_id);
}
