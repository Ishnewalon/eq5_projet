package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> getAllByManagerSignatureNull();
    Contract getContractByStudent_Matricule(String matricule);
    Contract getContractById(Long id);
    List<Contract> getAllByOffer_CreatorIdAndAndMonitorSignatureNull(Long id);
}
