package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<Contract> getAllUnsignedContracts() {
        return contractRepository.getAllByManagerSignatureNull();
    }

    public Contract managerSignContract(OfferApplication offerApplication) {
        Student student = getStudentFromCurriculum(offerApplication.getCurriculum());
        Contract contract = contractRepository.getContractByStudent_Matricule(student.getMatricule());
        contract.setManagerSignDate(new Date());
        contract.setContractPDF(createContractPDF(offerApplication));
        return contractRepository.save(contract);
    }

    private Student getStudentFromCurriculum(Curriculum curriculum) {
        Student student = curriculum.getStudent();
        return student;
    }

    private byte[] createContractPDF(OfferApplication offerApplication) {
        return null;
    }
}
