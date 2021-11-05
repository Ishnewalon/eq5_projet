package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.ContractStarterDto;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyHaveAContractException;
import com.gestionnaire_de_stage.model.*;;
import com.gestionnaire_de_stage.repository.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;

import javax.persistence.Id;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;


@Service
public class ContractService {

    private final ContractRepository contractRepository;

    private final ManagerService managerService;

    private final MonitorService monitorService;

    private final StudentService studentService;

    private final OfferApplicationService offerApplicationService;

    public ContractService(ContractRepository contractRepository,
                           ManagerService managerService,
                           MonitorService monitorService,
                           StudentService studentService,
                           OfferApplicationService offerApplicationService) {
        this.contractRepository = contractRepository;
        this.managerService = managerService;
        this.monitorService = monitorService;
        this.studentService = studentService;
        this.offerApplicationService = offerApplicationService;
    }

    public List<Contract> getAllUnsignedContracts() {
        return contractRepository.getAllByManagerSignatureNull();
    }

    public Contract addManagerSignature(String managerSignature, Long contract_id) throws IllegalArgumentException, IdDoesNotExistException {
        Assert.isTrue(managerSignature != null, "Il faut une signature");
        Assert.isTrue(contract_id != null, "L'id du contrat ne peut pas être null");
        if (isContractIdNotValid(contract_id))
            throw new IdDoesNotExistException();

        Contract contract = contractRepository.getContractByIdAndManagerSignatureNullAndMonitorSignatureNullAndStudentSignatureNull(contract_id);

        contract.setManagerSignDate(LocalDate.now());
        contract.setManagerSignature(managerSignature);
        return contractRepository.save(contract);
    }

    public List<Contract> getAllUnsignedContractForMonitor(Long monitor_id) throws IdDoesNotExistException {
        Assert.isTrue(monitor_id != null, "L'id du moniteur ne peut pas être null");
        if (monitorService.isIdInvalid(monitor_id)) {
            throw new IdDoesNotExistException();
        }
        return contractRepository.getAllByOffer_CreatorIdAndMonitorSignatureNullAndManagerSignatureNotNull(monitor_id);
    }

    public Contract addMonitorSignature(String monitorSignature, Long contract_id) throws IllegalArgumentException, IdDoesNotExistException {
        Assert.isTrue(monitorSignature != null, "Il faut une signature");
        Assert.isTrue(contract_id != null, "L'id du contrat ne peut pas être null");
        if (isContractIdNotValid(contract_id)) {
            throw new IdDoesNotExistException();
        }
        Contract contract = contractRepository.getContractByIdAndManagerSignatureNotNullAndMonitorSignatureNullAndStudentSignatureNull(contract_id);
        contract.setMonitorSignDate(LocalDate.now());
        contract.setMonitorSignature(monitorSignature);
        return contractRepository.save(contract);
    }

    public Contract getContractByStudentId(Long student_id) throws IdDoesNotExistException {
        Assert.isTrue(student_id != null, "L'id de l'étudiant ne peut pas être null");
        if (studentService.isIDNotValid(student_id)) {
            throw new IdDoesNotExistException();
        }
        return contractRepository.getContractByStudent_IdAndManagerSignatureNotNullAndMonitorSignatureNotNullAndStudentSignatureNull(student_id);
    }

    public Contract addStudentSignature(String studentSignature, Long contract_id) throws IdDoesNotExistException {
        Assert.isTrue(studentSignature != null, "Il faut une signature");
        Assert.isTrue(contract_id != null, "L'id du contrat ne peut pas être null");
        if (isContractIdNotValid(contract_id)) {
            throw new IdDoesNotExistException();
        }
        Contract contract = contractRepository.getContractByIdAndMonitorSignatureNotNullAndManagerSignatureNotNullAndStudentSignatureNull(contract_id);
        contract.setStudentSignDate(LocalDate.now());
        contract.setStudentSignature(studentSignature);
        return contractRepository.save(contract);
    }

    public Contract fillPDF(Contract contract, ByteArrayOutputStream baos) {
        Assert.isTrue(contract != null, "Le contract ne peut pas être null");
        contract.setContractPDF(baos.toByteArray());
        return contractRepository.save(contract);
    }

    private boolean isContractIdNotValid(Long contract_id) {
        return !contractRepository.existsById(contract_id);
    }

    public Contract gsStartContract(Contract contract, ContractStarterDto contractStarterDto) throws IdDoesNotExistException, IllegalArgumentException, StudentAlreadyHaveAContractException {
        Manager manager = managerService.getOneByID(contractStarterDto.getIdManager());
        contract.setManager(manager);

        OfferApplication offerApplication = offerApplicationService.getOneById(contractStarterDto.getIdOfferApplication());
        //TODO : set status
        contract.setOffer(offerApplication.getOffer());
        Curriculum curriculum = offerApplication.getCurriculum();
        Student student = curriculum.getStudent();
        if (doesStudentAlreadyHaveAContract(student.getId()))
            throw new StudentAlreadyHaveAContractException();
        contract.setStudent(student);

        return contractRepository.save(contract);
    }

    public boolean doesStudentAlreadyHaveAContract(Long id) {
        return contractRepository.existsByStudentId(id);
    }

    public Contract updateContract(Contract contract) {
        return contractRepository.save(contract);
    }
}
