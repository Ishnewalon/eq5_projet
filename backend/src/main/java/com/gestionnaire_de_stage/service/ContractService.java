package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.repository.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    public ContractService(ContractRepository contractRepository,
                           ManagerService managerService,
                           MonitorService monitorService,
                           StudentService studentService) {
        this.contractRepository = contractRepository;
        this.managerService = managerService;
        this.monitorService = monitorService;
        this.studentService = studentService;
    }

    public List<Contract> getAllUnsignedContracts() {
        return contractRepository.getAllByManagerSignatureNull();
    }

    public Contract addManagerSignature(String managerSignature, Long contract_id, Long manager_id) throws IllegalArgumentException, IdDoesNotExistException {
        Assert.isTrue(managerSignature != null, "Il faut une signature");
        Assert.isTrue(contract_id != null, "L'id du contrat ne peut pas être null");
        Assert.isTrue(manager_id != null, "L'id du gestionnaire ne peut pas être null");
        if (isContractIdNotValid(contract_id) || managerService.isIDNotValid(manager_id)) {
            throw new IdDoesNotExistException();
        }
        Contract contract = contractRepository.getContractById(contract_id);
        contract.setManagerSignDate(LocalDate.now());
        contract.setManagerSignature(managerSignature);
        contract.setManager(managerService.getOneByID(manager_id));
        return contractRepository.save(contract);
    }

    public List<Contract> getAllUnsignedContractForMonitor(Long monitor_id) throws IdDoesNotExistException {
        Assert.isTrue(monitor_id != null, "L'id du moniteur ne peut pas être null");
        if (monitorService.isIdInvalid(monitor_id)) {
            throw new IdDoesNotExistException();
        }
        return contractRepository.getAllByOffer_CreatorIdAndAndMonitorSignatureNullAndManagerSignatureNotNull(monitor_id);
    }

/*    public Contract getContractById(Long contract_id) throws IdDoesNotExistException {
        Assert.isTrue(contract_id != null, "L'id du contrat ne peut pas être null");
        if (isContractIdNotValid(contract_id)) {
            throw new IdDoesNotExistException();
        }
        return contractRepository.getContractById(contract_id);
    }*/

    public Contract addMonitorSignature(String monitorSignature, Long contract_id) throws IllegalArgumentException, IdDoesNotExistException {
        Assert.isTrue(monitorSignature != null, "Il faut une signature");
        Assert.isTrue(contract_id != null, "L'id du contrat ne peut pas être null");
        if (isContractIdNotValid(contract_id)) {
            throw new IdDoesNotExistException();
        }
        Contract contract = contractRepository.getContractById(contract_id);
        contract.setMonitorSignDate(LocalDate.now());
        contract.setMonitorSignature(monitorSignature);
        return contractRepository.save(contract);
    }

    public Contract getContractByStudentId(Long student_id) throws IdDoesNotExistException {
        Assert.isTrue(student_id != null, "L'id de l'étudiant ne peut pas être null");
        if (studentService.isIDNotValid(student_id)) {
            throw new IdDoesNotExistException();
        }
        return contractRepository.getContractByStudentId(student_id);
    }

    public Contract addStudentSignature(String studentSignature, Long contract_id) throws IdDoesNotExistException {
        Assert.isTrue(studentSignature != null, "Il faut une signature");
        Assert.isTrue(contract_id != null, "L'id du contrat ne peut pas être null");
        if (isContractIdNotValid(contract_id)) {
            throw new IdDoesNotExistException();
        }
        Contract contract = contractRepository.getContractById(contract_id);
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

}
