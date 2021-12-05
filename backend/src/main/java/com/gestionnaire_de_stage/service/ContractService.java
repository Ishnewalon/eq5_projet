package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.ContractStarterDto;
import com.gestionnaire_de_stage.dto.StudentMonitorOfferDTO;
import com.gestionnaire_de_stage.dto.UpdateStatusDTO;
import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.exception.*;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.ContractRepository;
import com.gestionnaire_de_stage.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ContractService {

    private final ContractRepository contractRepository;

    private final ManagerService managerService;

    private final MonitorService monitorService;

    private final StudentService studentService;

    private final OfferApplicationService offerApplicationService;

    private final StudentRepository studentRepository;

    public ContractService(ContractRepository contractRepository,
                           ManagerService managerService,
                           MonitorService monitorService,
                           StudentService studentService,
                           OfferApplicationService offerApplicationService,
                           StudentRepository studentRepository) {
        this.contractRepository = contractRepository;
        this.managerService = managerService;
        this.monitorService = monitorService;
        this.studentService = studentService;
        this.offerApplicationService = offerApplicationService;
        this.studentRepository = studentRepository;
    }

    public List<Contract> getAllUnsignedContracts() {
        return contractRepository.getAllByManagerSignatureNull();
    }

    public Contract getOneById(Long idContract) throws IdDoesNotExistException {
        Assert.notNull(idContract, "L'identifiant du contrat ne peut pas être vide");
        if (!contractRepository.existsById(idContract))
            throw new IdDoesNotExistException("Aucun contrat ne correspond à cet identifiant");
        return contractRepository.getById(idContract);
    }

    public Contract addManagerSignature(String managerSignature, Long contract_id) throws IllegalArgumentException, IdDoesNotExistException {
        Assert.isTrue(managerSignature != null, "Il faut une signature");
        Assert.isTrue(contract_id != null, "L'identifiant du contrat ne peut pas être vide");
        if (isContractIdNotValid(contract_id))
            throw new IdDoesNotExistException("Il n'y a pas de contrat associé à cet identifiant");

        Contract contract = contractRepository.getContractByIdAndManagerSignatureNullAndMonitorSignatureNullAndStudentSignatureNullAndSession_YearGreaterThanEqual(contract_id, Year.now());

        contract.setManagerSignDate(LocalDate.now());
        contract.setManagerSignature(managerSignature);
        return contractRepository.save(contract);
    }

    public List<Contract> getAllUnsignedContractForMonitor(Long monitor_id) throws IdDoesNotExistException {
        Assert.isTrue(monitor_id != null, "L'identifiant du moniteur ne peut pas être vide");
        if (monitorService.isIdInvalid(monitor_id)) {
            throw new IdDoesNotExistException("Il n'y a pas de moniteur associé à cet identifiant");
        }
        return contractRepository.getAllByOffer_CreatorIdAndMonitorSignatureNullAndManagerSignatureNotNullAndSession_YearGreaterThanEqual(monitor_id, Year.now());
    }

    public Contract addMonitorSignature(String monitorSignature, Long contract_id) throws IllegalArgumentException, IdDoesNotExistException {
        Assert.isTrue(monitorSignature != null, "Il faut une signature");
        Assert.isTrue(contract_id != null, "L'identifiant du contrat ne peut pas être vide");
        if (isContractIdNotValid(contract_id)) {
            throw new IdDoesNotExistException("Il n'y a pas de contrat associé à cet identifiant");
        }
        Contract contract = contractRepository.getContractByIdAndManagerSignatureNotNullAndMonitorSignatureNullAndStudentSignatureNull(contract_id);
        contract.setMonitorSignDate(LocalDate.now());
        contract.setMonitorSignature(monitorSignature);
        return contractRepository.save(contract);
    }

    public Contract getContractByStudentId(Long student_id) throws IdDoesNotExistException {
        Assert.isTrue(student_id != null, "L'identifiant de l'étudiant ne peut pas être vide");
        if (studentService.isIDNotValid(student_id)) {
            throw new IdDoesNotExistException("Il n'y a pas d'étudiant associé à cet identifiant");
        }
        return contractRepository.getByStudent_IdAndManagerSignatureNotNullAndMonitorSignatureNotNullAndStudentSignatureNullAndSession_YearGreaterThanEqual(student_id, Year.now());
    }

    public Contract getContractByStudentMatricule(String matricule) throws MatriculeDoesNotExistException, ContractDoesNotExistException {
        Assert.isTrue(matricule != null, "La matricule de l'étudiant ne peut pas être vide");
        if (!studentRepository.existsByMatricule(matricule)) {
            throw new MatriculeDoesNotExistException("Il n'y a pas d'étudiant ayant la matricule " + matricule);
        }
        if (isNotCreated(matricule)) {
            throw new ContractDoesNotExistException("Il n'y a pas de contrat qui existe pour la matricule " + matricule);
        }
        return contractRepository.getContractByStudent_Matricule(matricule);
    }

    public Contract addStudentSignature(String studentSignature, Long contract_id) throws IdDoesNotExistException {
        Assert.isTrue(studentSignature != null, "Il faut une signature");
        Assert.isTrue(contract_id != null, "L'identifiant du contrat ne peut pas être vide");
        if (isContractIdNotValid(contract_id)) {
            throw new IdDoesNotExistException("Il n'y a pas de contrat associé à cet identifiant");
        }
        Contract contract = contractRepository.getContractByIdAndMonitorSignatureNotNullAndManagerSignatureNotNullAndStudentSignatureNull(contract_id);
        contract.setStudentSignDate(LocalDate.now());
        contract.setStudentSignature(studentSignature);
        return contractRepository.save(contract);
    }

    public Contract fillPDF(Contract contract, ByteArrayOutputStream baos) {
        Assert.isTrue(contract != null, "Le contrat ne peut pas être vide");
        contract.setContractPDF(baos.toByteArray());
        return contractRepository.save(contract);
    }

    private boolean isContractIdNotValid(Long contract_id) {
        return !contractRepository.existsByIdAndSession_YearGreaterThanEqual(contract_id, Year.now());
    }

    public Contract gsStartContract(Contract contract, ContractStarterDto contractStarterDto) throws IdDoesNotExistException, IllegalArgumentException, StudentAlreadyHaveAContractException, StudentIsNotAssignedException {
        Manager manager = managerService.getOneByID(contractStarterDto.getIdManager());
        contract.setManager(manager);

        OfferApplication offerApplication = offerApplicationService.getOneById(contractStarterDto.getIdOfferApplication());

        Offer offer = offerApplication.getOffer();
        Monitor monitor = offer.getCreator();
        Curriculum curriculum = offerApplication.getCurriculum();
        Student student = curriculum.getStudent();

        if (doesStudentAlreadyHaveAContract(student.getId(), contract.getSession()))
            throw new StudentAlreadyHaveAContractException("Un contrat existe déjà pour l'étudiant ayant la matricule " + student.getMatricule());
        if (studentRepository.existsByIdAndSupervisorNull(student.getId())) {
            throw new StudentIsNotAssignedException("L'étudiant doit être affecté à un superviseur avant de créer un contrat");
        }

        offerApplicationService.updateStatus(new UpdateStatusDTO(offerApplication.getId(), Status.EN_SIGNATURE));

        contract.setStudent(student);
        contract.setMonitor(monitor);
        contract.setOffer(offer);
        contract.setSession(offerApplication.getSession());

        return contractRepository.save(contract);
    }

    public boolean doesStudentAlreadyHaveAContract(Long id, Session session) {
        return contractRepository.existsByStudentIdAndSession(id, session);
    }

    public Contract updateContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public List<Contract> getAllSignedContractsByManager(Long id) throws IllegalArgumentException {
        Assert.isTrue(id != null, "L'identifiant du gestionnaire ne peut pas être vide");
        return contractRepository.getAllByManager_IdAndManagerSignatureNotNullAndSession_YearGreaterThanEqual(id, Year.now());
    }

    public List<Contract> getAllSignedContractsByMonitor(Long monitor_id) {
        Assert.isTrue(monitor_id != null, "L'identifiant du moniteur ne peut pas être vide");
        return contractRepository.getAllByMonitor_IdAndManagerSignatureNotNullAndMonitorSignatureNotNullAndSession_YearGreaterThanEqual(monitor_id, Year.now());
    }

    public Contract getSignedContractByStudentId(Long student_id) throws IdDoesNotExistException {
        Assert.isTrue(student_id != null, "L'identifiant de l'étudiant ne peut pas être vide");
        if (studentService.isIDNotValid(student_id)) {
            throw new IdDoesNotExistException("Il n'y a pas d'étudiant associé à cet identifiant");
        }
        return contractRepository.getByStudent_IdAndManagerSignatureNotNullAndMonitorSignatureNotNullAndStudentSignatureNotNullAndSession_YearGreaterThanEqual(student_id, Year.now());
    }

    public StudentMonitorOfferDTO buildStudentMonitorOfferDTOFromContract(Contract contract) throws IllegalArgumentException {
        Assert.notNull(contract, "Le contrat ne peut pas être vide");

        return new StudentMonitorOfferDTO(
                contract.getStudent(),
                contract.getMonitor(),
                contract.getOffer()
        );
    }

    public List<StudentMonitorOfferDTO> stageListToStudentMonitorOfferDtoList(List<Stage> stageList) {
        Assert.notNull(stageList, "La liste de stage ne peut pas être vide");

        return stageList.stream().map((stage) ->
                        buildStudentMonitorOfferDTOFromContract(stage.getContract()))
                .collect(Collectors.toList());
    }

    public boolean isNotCreated(String matricule) {
        return !contractRepository.existsByStudentMatricule(matricule);
    }
}
