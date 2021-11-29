package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.ContractStarterDto;
import com.gestionnaire_de_stage.dto.StudentMonitorOfferDTO;
import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.exception.*;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.ContractRepository;
import com.gestionnaire_de_stage.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContractServiceTest {

    @InjectMocks
    private ContractService contractService;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ManagerService managerService;

    @Mock
    private MonitorService monitorService;

    @Mock
    private StudentService studentService;

    @Mock
    private OfferApplicationService offerApplicationService;

    @Mock
    private StudentRepository studentRepository;

    @Test
    public void testGetAllByManagerSignatureNull() {
        when(contractRepository.getAllByManagerSignatureNull()).thenReturn(getDummyContractList());

        final List<Contract> actualContractList = contractService.getAllUnsignedContracts();

        assertThat(actualContractList.size()).isEqualTo(getDummyContractList().size());
    }

    @Test
    public void testAddManagerSignature_withValidEntries() throws Exception {
        String managerSignature = "Joe Janson";
        Contract dummyContract = getDummyContract();
        Manager dummyManager = getDummyManager();
        when(contractRepository.existsByIdAndSession_YearGreaterThanEqual(any(), any())).thenReturn(true);
        when(contractRepository.getContractByIdAndManagerSignatureNullAndMonitorSignatureNullAndStudentSignatureNullAndSession_YearGreaterThanEqual(any(), any())).thenReturn(dummyContract);
        when(contractRepository.save(any())).thenReturn(getDummyFilledContract());

        Contract actualContract = contractService.addManagerSignature(managerSignature, dummyContract.getId());
        assertThat(actualContract.getManager()).isEqualTo(dummyManager);
    }

    @Test
    public void testAddManagerSignature_withNullManagerSignature() {
        Contract dummyContract = getDummyContract();
        assertThrows(IllegalArgumentException.class,
                () -> contractService.addManagerSignature(null, dummyContract.getId()));
    }

    @Test
    public void testAddManagerSignature_withNullContractID() {
        String managerSignature = "Joe Janson";
        assertThrows(IllegalArgumentException.class,
                () -> contractService.addManagerSignature(managerSignature, null));
    }

    @Test
    public void testAddManagerSignature_withInvalidContractID() {
        String managerSignature = "Joe Janson";
        Contract dummyContract = getDummyContract();
        when(contractRepository.existsByIdAndSession_YearGreaterThanEqual(any(), any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> contractService.addManagerSignature(managerSignature, dummyContract.getId()));
    }

    @Test
    public void testGsStartContract() throws Exception {
        Manager dummyManager = getDummyManager();
        OfferApplication dummyOfferApplication = getDummyOfferApplication();
        Contract dummyFilledContract = getDummyFilledContract();
        when(managerService.getOneByID(any())).thenReturn(dummyManager);
        when(offerApplicationService.getOneById(any())).thenReturn(dummyOfferApplication);
        when(contractRepository.save(any())).thenReturn(dummyFilledContract);
        when(contractService.doesStudentAlreadyHaveAContract(any(), any())).thenReturn(false);
        when(studentService.isStudentNotAssigned(any())).thenReturn(false);

        Contract contract = contractService.gsStartContract(getDummyContract(), new ContractStarterDto(dummyManager.getId(), dummyOfferApplication.getId()));

        assertThat(contract.getId()).isEqualTo(dummyFilledContract.getId());
    }

    @Test
    public void testGsStartContract_whenStudentAlreadyHaveAContract() throws Exception {
        Manager dummyManager = getDummyManager();
        OfferApplication dummyOfferApplication = getDummyOfferApplication();
        when(managerService.getOneByID(any())).thenReturn(dummyManager);
        when(offerApplicationService.getOneById(any())).thenReturn(dummyOfferApplication);
        when(contractService.doesStudentAlreadyHaveAContract(any(), any())).thenReturn(true);

        assertThrows(StudentAlreadyHaveAContractException.class,
                () -> contractService.gsStartContract(getDummyContract(), new ContractStarterDto(dummyManager.getId(), dummyOfferApplication.getId())));
    }

    @Test
    public void testGsStartContract_whenStudentNotAssigneSupervisor() throws Exception {
        Manager dummyManager = getDummyManager();
        OfferApplication dummyOfferApplication = getDummyOfferApplication();
        when(managerService.getOneByID(any())).thenReturn(dummyManager);
        when(offerApplicationService.getOneById(any())).thenReturn(dummyOfferApplication);
        when(contractService.doesStudentAlreadyHaveAContract(any(), any())).thenReturn(false);
        when(studentService.isStudentNotAssigned(any())).thenReturn(true);

        assertThrows(StudentIsNotAssignedException.class,
                () -> contractService.gsStartContract(getDummyContract(), new ContractStarterDto(dummyManager.getId(), dummyOfferApplication.getId())));
    }

    @Test
    void testUpdateContract() {
        Contract dummyFilledContract = getDummyFilledContract();
        when(contractRepository.save(any())).thenReturn(dummyFilledContract);

        Contract contract = contractService.updateContract(getDummyContract());

        assertThat(contract).isEqualTo(dummyFilledContract);
    }


    @Test
    public void testFillPDF_withValidEntries() {
        Contract dummyContract = getDummyFilledContract();
        when(contractRepository.save(any())).thenReturn(dummyContract);

        Contract actualContract = contractService.fillPDF(new Contract(), new ByteArrayOutputStream());

        assertThat(actualContract.getContractPDF()).isEqualTo(dummyContract.getContractPDF());
        assertThat(actualContract.getManagerSignature()).isEqualTo(dummyContract.getManagerSignature());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testFillPDF_withNullContractId() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        assertThrows(IllegalArgumentException.class,
                () -> contractService.fillPDF(null, baos));
    }

    @Test
    public void testGetAllUnsignedContractForMonitor_withValidEntries() throws Exception {
        List<Contract> dummyContractList = getDummyContractList();
        long monitor_id = 1L;
        when(monitorService.isIdInvalid(any())).thenReturn(false);
        when(contractRepository.getAllByOffer_CreatorIdAndMonitorSignatureNullAndManagerSignatureNotNullAndSession_YearGreaterThanEqual(any(), any()))
                .thenReturn(dummyContractList);


        List<Contract> actualContractList = contractService.getAllUnsignedContractForMonitor(monitor_id);

        assertThat(actualContractList.get(1).getId()).isEqualTo(dummyContractList.get(1).getId());
    }

    @Test
    public void testGetAllUnsignedContractForMonitor_withNullMonitorID() {
        assertThrows(IllegalArgumentException.class,
                () -> contractService.getAllUnsignedContractForMonitor(null));
    }

    @Test
    public void testGetAllUnsignedContractForMonitor_withInvalidMonitorID() {
        long monitor_id = 1L;
        when(monitorService.isIdInvalid(any())).thenReturn(true);

        assertThrows(IdDoesNotExistException.class,
                () -> contractService.getAllUnsignedContractForMonitor(monitor_id));
    }

    @Test
    public void testAddMonitorSignature_validValidEntries() throws Exception {
        String monitorSignature = "Joe Janson";
        Contract dummyContract = getDummyContract();
        when(contractRepository.existsByIdAndSession_YearGreaterThanEqual(any(), any())).thenReturn(true);
        when(contractRepository.getContractByIdAndManagerSignatureNotNullAndMonitorSignatureNullAndStudentSignatureNull(any())).thenReturn(dummyContract);
        when(contractRepository.save(any())).thenReturn(getDummyFilledContract());

        Contract actualContract = contractService.addMonitorSignature(monitorSignature, dummyContract.getId());
        assertThat(actualContract.getStudent().getFirstName()).isEqualTo(dummyContract.getStudent().getFirstName());
    }

    @Test
    public void testAddMonitorSignature_withNullMonitorSignature() {
        Contract dummyContract = getDummyContract();
        assertThrows(IllegalArgumentException.class,
                () -> contractService.addMonitorSignature(null, dummyContract.getId()));
    }

    @Test
    public void testAddMonitorSignature_withNullContractID() {
        String monitorSignature = "Joe Janson";
        assertThrows(IllegalArgumentException.class,
                () -> contractService.addMonitorSignature(monitorSignature, null));
    }

    @Test
    public void testAddMonitorSignature_withInvalidContractID() {
        String monitorSignature = "Joe Janson";
        long contract_id = 1L;
        when(contractRepository.existsByIdAndSession_YearGreaterThanEqual(any(), any())).thenReturn(false);
        assertThrows(IdDoesNotExistException.class,
                () -> contractService.addMonitorSignature(monitorSignature, contract_id));

    }

    @Test
    public void testGetContractByStudentId_withValidEntries() throws Exception {
        Contract dummyContract = getDummyContract();
        when(studentService.isIDNotValid(any())).thenReturn(false);
        when(contractRepository.getByStudent_IdAndManagerSignatureNotNullAndMonitorSignatureNotNullAndStudentSignatureNullAndSession_YearGreaterThanEqual(any(),any())).thenReturn(dummyContract);

        Student dummyStudent = dummyContract.getStudent();
        Contract actualContract = contractService.getContractByStudentId(dummyStudent.getId());

        Student student = actualContract.getStudent();

        assertThat(student.getFirstName()).isEqualTo(dummyStudent.getFirstName());

    }

    @Test
    public void testGetContractByStudentId_withNullStudentId() {
        assertThrows(IllegalArgumentException.class,
                () -> contractService.getContractByStudentId(null));
    }

    @Test
    public void testGetContractByStudentId_withInvalidStudentId() {
        Contract dummyContract = getDummyContract();
        when(studentService.isIDNotValid(any())).thenReturn(true);

        assertThrows(IdDoesNotExistException.class,
                () -> contractService.getContractByStudentId(dummyContract.getStudent().getId()));
    }

    @Test
    public void testGetContractByStudentMatricule_withValidEntries() throws Exception {
        Contract dummyContract = getDummyContract();
        String matricule = "1234567";
        when(studentRepository.existsByMatricule(any())).thenReturn(true);
        when(contractRepository.existsByStudentMatricule(any())).thenReturn(true);
        when(contractRepository.getContractByStudent_Matricule(any())).thenReturn(dummyContract);

        Contract actualContract = contractService.getContractByStudentMatricule(matricule);

        assertThat(actualContract).isEqualTo(dummyContract);
        assertThat(actualContract.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetContractByStudentMatricule_withNullMatricule() {
        assertThrows(IllegalArgumentException.class,
                () -> contractService.getContractByStudentMatricule(null));
    }

    @Test
    public void testGetContractByStudentMatricule_withInvalidMatricule() {
        String matricule = "1234567";
        when(studentRepository.existsByMatricule(any())).thenReturn(false);

        assertThrows(MatriculeDoesNotExistException.class,
                () -> contractService.getContractByStudentMatricule(matricule));
    }

    @Test
    public void testGetContractByStudentMatricule_withInexistingContract() {
        String matricule = "1234567";
        when(studentRepository.existsByMatricule(any())).thenReturn(true);
        when(contractRepository.existsByStudentMatricule(any())).thenReturn(false);

        assertThrows(ContractDoesNotExistException.class,
                () -> contractService.getContractByStudentMatricule(matricule));
    }


    @Test
    public void testAddStudentSignature_withValidEntries() throws Exception {
        Contract dummyContract = getDummyContract();
        String studentSignature = "Dawn Soap";
        when(contractRepository.existsByIdAndSession_YearGreaterThanEqual(any(), any())).thenReturn(true);
        when(contractRepository.getContractByIdAndMonitorSignatureNotNullAndManagerSignatureNotNullAndStudentSignatureNull(any())).thenReturn(dummyContract);
        when(contractRepository.save(any())).thenReturn(dummyContract);

        Contract actualContract = contractService.addStudentSignature(studentSignature, dummyContract.getId());

        Student actualStudent = actualContract.getStudent();
        Student dummyStudent = dummyContract.getStudent();

        assertThat(actualStudent.getFirstName()).isEqualTo(dummyStudent.getFirstName());
    }

    @Test
    public void testAddStudentSignature_withNullEntries() {
        assertThrows(IllegalArgumentException.class,
                () -> contractService.addStudentSignature(null, null));
    }

    @Test
    public void testAddStudentSignature_withInvalidContractId() {
        Contract dummyContract = getDummyContract();
        String studentSignature = "Dawn Soap";
        when(contractRepository.existsByIdAndSession_YearGreaterThanEqual(any(), any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> contractService.addStudentSignature(studentSignature, dummyContract.getId()));
    }


    @Test
    public void testGetAllSignedContracts_withExistentId() {
        List<Contract> dummyContractList = getDummyContractList();
        when(contractRepository.getAllByManager_IdAndManagerSignatureNotNullAndSession_YearGreaterThanEqual(any(), any())).thenReturn(dummyContractList);

        List<Contract> actualContractList = contractService.getAllSignedContractsByManager(1L);

        assertThat(actualContractList)
                .isNotEmpty()
                .isEqualTo(dummyContractList);
    }

    @Test
    public void testGetAllSignedContracts_withNonExistentId() {
        when(contractRepository.getAllByManager_IdAndManagerSignatureNotNullAndSession_YearGreaterThanEqual(any(), any())).thenReturn(Collections.emptyList());

        List<Contract> allSignedContractsByManager = contractService.getAllSignedContractsByManager(1000L);

        assertThat(allSignedContractsByManager).isEmpty();
    }

    @Test
    public void testGetAllSignedContracts_withNullId() {
        assertThrows(IllegalArgumentException.class,
                () -> contractService.getAllSignedContractsByManager(null));
    }

    @Test
    public void testGetAllSignedContractsForMonitor_withExistentId() {
        List<Contract> dummyContractList = getDummyContractList();
        when(contractRepository.getAllByMonitor_IdAndManagerSignatureNotNullAndMonitorSignatureNotNullAndSession_YearGreaterThanEqual(any(), any())).thenReturn(dummyContractList);

        List<Contract> actualContractList = contractService.getAllSignedContractsByMonitor(1L);

        assertThat(actualContractList)
                .isNotEmpty()
                .isEqualTo(dummyContractList);
    }

    @Test
    public void testGetAllSignedContractsForMonitor_withNonExistentId() {
        when(contractRepository.getAllByMonitor_IdAndManagerSignatureNotNullAndMonitorSignatureNotNullAndSession_YearGreaterThanEqual(any(), any())).thenReturn(Collections.emptyList());

        List<Contract> actualContractList = contractService.getAllSignedContractsByMonitor(1000L);

        assertThat(actualContractList).isEmpty();
    }

    @Test
    public void testGetAllSignedContractsForMonitor_withNullId() {
        assertThrows(IllegalArgumentException.class,
                () -> contractService.getAllSignedContractsByMonitor(null));
    }

    @Test
    public void testGetSignedContractByStudent_withExistentId() throws IdDoesNotExistException {
        Contract dummyContract = getDummyContract();
        when(contractRepository.getByStudent_IdAndManagerSignatureNotNullAndMonitorSignatureNotNullAndStudentSignatureNotNullAndSession_YearGreaterThanEqual(any(), any())).thenReturn(dummyContract);
        when(studentService.isIDNotValid(any())).thenReturn(false);

        Contract actualContract = contractService.getSignedContractByStudentId(1L);

        assertThat(actualContract).isNotNull();
        assertThat(actualContract).isEqualTo(dummyContract);
    }

    @Test
    public void testGetSignedContractByStudent_withNonExistendId() {
        when(studentService.isIDNotValid(any())).thenReturn(true);

        assertThrows(IdDoesNotExistException.class,
                () -> contractService.getSignedContractByStudentId(1L)
        );
    }

    @Test
    public void testGetSignedContractByStudent_withNullId() {
        assertThrows(IllegalArgumentException.class,
                () -> contractService.getSignedContractByStudentId(null),
                "L'id de l'étudiant ne peut pas être null");
    }

    @Test
    public void testBuildStudentMonitorOfferDTOFromContract_withValidEntries() {
        Contract contract = getDummyContract();
        StudentMonitorOfferDTO studentMonitorOfferDTO = getDummyStudentMonitorOfferDTO();

        StudentMonitorOfferDTO actual = contractService.buildStudentMonitorOfferDTOFromContract(contract);

        assertThat(actual.getStudent()).isEqualTo(studentMonitorOfferDTO.getStudent());
        assertThat(actual.getMonitor()).isEqualTo(studentMonitorOfferDTO.getMonitor());
        assertThat(actual.getOffer()).isEqualTo(studentMonitorOfferDTO.getOffer());
    }

    @Test
    public void testBuildStudentMonitorOfferDTOFromContract_withNullEntries() {
        assertThrows(IllegalArgumentException.class,
                () -> contractService.buildStudentMonitorOfferDTOFromContract(null),
                "Le contract ne peut pas être null");
    }

    @Test
    public void testBuildStudentMonitorOfferDTOFromContract() {
        Contract contract = getDummyContract();
        StudentMonitorOfferDTO studentMonitorOfferDTO = getDummyStudentMonitorOfferDTO();

        StudentMonitorOfferDTO actual = contractService.buildStudentMonitorOfferDTOFromContract(contract);

        assertThat(actual.getStudent()).isEqualTo(studentMonitorOfferDTO.getStudent());
        assertThat(actual.getMonitor()).isEqualTo(studentMonitorOfferDTO.getMonitor());
        assertThat(actual.getOffer()).isEqualTo(studentMonitorOfferDTO.getOffer());
    }

    @Test
    public void testStageListToStudentMonitorOfferDtoList() {
        List<Stage> stageList = getDummyStageList();
        List<StudentMonitorOfferDTO> studentMonitorOfferDTOList = getDummyStudentMonitorOfferDTOList();

        List<StudentMonitorOfferDTO> actual = contractService.stageListToStudentMonitorOfferDtoList(stageList);

        assertThat(actual.size()).isEqualTo(studentMonitorOfferDTOList.size());
    }

    @Test
    public void testStageListToStudentMonitorOfferDtoList_throwsIllegalArg() {
        assertThrows(IllegalArgumentException.class,
                () -> contractService.stageListToStudentMonitorOfferDtoList(null),
                "La liste de stage ne peut pas être null");
    }

    private List<StudentMonitorOfferDTO> getDummyStudentMonitorOfferDTOList() {
        StudentMonitorOfferDTO dto1 = getDummyStudentMonitorOfferDTO();
        StudentMonitorOfferDTO dto2 = getDummyStudentMonitorOfferDTO();
        StudentMonitorOfferDTO dto3 = getDummyStudentMonitorOfferDTO();
        return new ArrayList<>(Arrays.asList(dto1, dto2, dto3));
    }

    private List<Stage> getDummyStageList() {
        Stage stage1 = getDummyStage();
        Stage stage2 = getDummyStage();
        stage2.setId(2L);
        Stage stage3 = getDummyStage();
        stage3.setId(3L);
        return new ArrayList<>(Arrays.asList(stage1, stage2, stage3));
    }

    private Stage getDummyStage() {
        Stage dummyStage = new Stage();
        dummyStage.setId(1L);
        dummyStage.setContract(getDummyContract());
        return dummyStage;
    }

    private StudentMonitorOfferDTO getDummyStudentMonitorOfferDTO() {
        return new StudentMonitorOfferDTO(
                getDummyStudent(),
                getDummyMonitor(),
                getDummyOffer()
        );
    }

    private Monitor getDummyMonitor() {
        Monitor dummyMonitor = new Monitor();
        dummyMonitor.setId(1L);
        dummyMonitor.setFirstName("same");
        dummyMonitor.setLastName("dude");
        dummyMonitor.setEmail("dudesame@gmail.com");
        dummyMonitor.setPhone("5145555112");
        dummyMonitor.setDepartment("Informatique");
        dummyMonitor.setPassword("testPassword");
        return dummyMonitor;
    }

    private List<Contract> getDummyContractList() {
        List<Contract> dummyContractList = new ArrayList<>();
        Contract contract1 = new Contract();
        contract1.setId(1L);
        contract1.setStudent(new Student());
        dummyContractList.add(contract1);
        Contract contract2 = new Contract();
        contract2.setId(2L);
        contract1.setStudent(new Student());
        dummyContractList.add(contract2);
        Contract contract3 = new Contract();
        contract3.setId(3L);
        contract1.setStudent(new Student());
        dummyContractList.add(contract3);

        return dummyContractList;
    }

    private Contract getDummyContract() {
        Contract dummyContract = new Contract();
        dummyContract.setId(1L);
        dummyContract.setStudent(getDummyStudent());
        dummyContract.setMonitor(getDummyMonitor());
        dummyContract.setOffer(getDummyOffer());
        return dummyContract;
    }

    private OfferApplication getDummyOfferApplication() {
        OfferApplication offerApplicationDTO = new OfferApplication();
        offerApplicationDTO.setOffer(getDummyOffer());
        offerApplicationDTO.setCurriculum(getDummyCurriculum());
        offerApplicationDTO.setId(1L);
        offerApplicationDTO.setStatus(Status.CV_ENVOYE);

        return offerApplicationDTO;
    }

    private Curriculum getDummyCurriculum() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);

        return new Curriculum(
                "fileName",
                "content type",
                "test".getBytes(),
                dummyStudent
        );
    }

    private Offer getDummyOffer() {
        Offer dummyOffer = new Offer();
        dummyOffer.setDepartment("Un departement");
        dummyOffer.setAddress("ajsaodas");
        dummyOffer.setId(1L);
        dummyOffer.setDescription("oeinoiendw");
        dummyOffer.setSalary(10);
        dummyOffer.setTitle("oeinoiendw");
        return dummyOffer;
    }

    private Contract getDummyFilledContract() {
        Contract dummyContract = new Contract();
        dummyContract.setManager(getDummyManager());
        dummyContract.setManagerSignature("Joe Janson");
        dummyContract.setId(1L);
        dummyContract.setStudent(getDummyStudent());
        dummyContract.setContractPDF(new byte[]{5, 1, 9, 2, 6});
        return dummyContract;
    }

    private Student getDummyStudent() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        dummyStudent.setLastName("Candle");
        dummyStudent.setFirstName("Tea");
        dummyStudent.setEmail("cant@outlook.com");
        dummyStudent.setPassword("cantPass");
        dummyStudent.setDepartment("info");
        dummyStudent.setMatricule("4673943");
        return dummyStudent;
    }

    private Manager getDummyManager() {
        Manager dummyManager = new Manager();
        dummyManager.setPassword("Test1234");
        dummyManager.setEmail("oussamakably@gmail.com");
        dummyManager.setFirstName("Oussama");
        dummyManager.setLastName("Kably");
        dummyManager.setPhone("5143643320");
        return dummyManager;
    }

}
