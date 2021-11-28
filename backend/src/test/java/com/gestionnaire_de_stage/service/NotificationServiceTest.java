package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private CurriculumService curriculumService;

    @Mock
    private ManagerService managerService;

    @Mock
    private StudentService studentService;

    @Mock
    private OfferService offerService;

    @Mock
    private OfferApplicationService offerApplicationService;

    @Mock
    private ContractService contractService;

    @Mock
    private NotificationRepository notificationRepository;

    @Test
    void testNotifyOfCurriculumValidation() throws IdDoesNotExistException {
        Curriculum curriculum = getDummyCurriculum();
        curriculum.setIsValid(true);
        when(curriculumService.getOneByID(any())).thenReturn(getDummyCurriculum());

        notificationService.notifyOfCurriculumValidation(curriculum);

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    void testNotifyOfCurriculumValidation_throwsIdDoesNotExist() throws IdDoesNotExistException {
        Curriculum curriculum = getDummyCurriculum();
        curriculum.setIsValid(true);
        when(curriculumService.getOneByID(any())).thenThrow(IdDoesNotExistException.class);

        assertThrows(IdDoesNotExistException.class, () ->
                notificationService.notifyOfCurriculumValidation(curriculum));
    }

    @Test
    void testNotifyOfCurriculumValidation_throwsIllegalArg() throws IdDoesNotExistException {
        Curriculum curriculum = getDummyCurriculum();
        curriculum.setIsValid(true);
        when(curriculumService.getOneByID(any())).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () ->
                notificationService.notifyOfCurriculumValidation(curriculum));
    }

    @Test
    void testNotifyOfCurriculumValidation_withNullParam() {
        assertThrows(IllegalArgumentException.class, () ->
                notificationService.notifyOfCurriculumValidation(null));
    }

    @Test
    void testNotifyOfCurriculumValidation_doesNothingWhenValidNoChange() throws IdDoesNotExistException {
        Curriculum curriculum = getDummyCurriculum();
        when(curriculumService.getOneByID(any())).thenReturn(curriculum);

        notificationService.notifyOfCurriculumValidation(curriculum);

        verify(notificationRepository, times(0)).save(any());
    }

    @Test
    void testNotifyOfNewApplicant() {
        OfferApplication offerApplication = getDummyOfferApp();

        notificationService.notifyOfNewApplicant(offerApplication);

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    void testNotifyOfNewApplicant_withNullParam() {
        assertThrows(IllegalArgumentException.class, () ->
                notificationService.notifyOfNewApplicant(null));
    }

    @Test
    void testNotifyOfNewOffer() {
        Offer offer = getDummyOffer();
        when(managerService.getAll()).thenReturn(getDummyManagerList());

        notificationService.notifyOfNewOffer(offer);

        verify(notificationRepository, times(3)).save(any());
    }

    @Test
    void testNotifyOfNewOffer_withNullParam() {
        assertThrows(IllegalArgumentException.class, () ->
                notificationService.notifyOfNewOffer(null));
    }

    @Test
    void testNotifyOfOfferValidation() {
        Offer offer = getDummyOffer();
        offer.setValid(true);
        when(offerService.findOfferById(any())).thenReturn(Optional.of(getDummyOffer()));
        when(studentService.getAll()).thenReturn(getDummyStudentList());

        notificationService.notifyOfOfferValidation(offer);

        verify(notificationRepository, times(4)).save(any());
    }

    @Test
    void testNotifyOfOfferValidation_withNullParam() {
        assertThrows(IllegalArgumentException.class, () ->
                notificationService.notifyOfOfferValidation(null));
    }

    @Test
    void testNotifyOfOfferValidation_cannotFindOffer() {
        Offer offer = getDummyOffer();
        offer.setValid(true);
        when(offerService.findOfferById(any())).thenReturn(Optional.empty());

        notificationService.notifyOfOfferValidation(offer);

        verify(notificationRepository, times(0)).save(any());
    }

    @Test
    void testNotifyOfOfferValidation_doesNothingWhenValidNoChange() {
        Offer offer = getDummyOffer();
        when(offerService.findOfferById(any())).thenReturn(Optional.of(getDummyOffer()));

        notificationService.notifyOfOfferValidation(offer);

        verify(notificationRepository, times(0)).save(any());
    }

    @Test
    void testNotifyOfOfferValidation_withOfferInvalidInvokeOnce() {
        Offer offer = getDummyOffer();
        offer.setValid(false);
        when(offerService.findOfferById(any())).thenReturn(Optional.of(getDummyOffer()));

        notificationService.notifyOfOfferValidation(offer);

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    void testNotifyOfOfferAppStatusSetToStageTrouve() throws IdDoesNotExistException {
        OfferApplication offerApplication = getDummyOfferApplication();
        offerApplication.setStatus(Status.STAGE_TROUVE);
        when(offerApplicationService.getOneById(any())).thenReturn(getDummyOfferApplication());
        when(managerService.getAll()).thenReturn(getDummyManagerList());

        notificationService.notifyOfOfferAppStatusSetToStageTrouve(offerApplication);

        verify(notificationRepository, times(3)).save(any());
    }

    @Test
    void testNotifyOfOfferAppStatusSetToStageTrouve_withNullParam() {
        assertThrows(IllegalArgumentException.class, () ->
                notificationService.notifyOfOfferAppStatusSetToStageTrouve(null));
    }

    @Test
    void testNotifyOfOfferAppStatusSetToStageTrouve_doesNothingWhenStatusNoChange() throws IdDoesNotExistException {
        OfferApplication offerApplication = getDummyOfferApplication();
        offerApplication.setStatus(Status.STAGE_REFUSE);
        when(offerApplicationService.getOneById(any())).thenReturn(getDummyOfferApplication());

        notificationService.notifyOfOfferAppStatusSetToStageTrouve(offerApplication);

        verify(notificationRepository, times(0)).save(any());
    }

    @Test
    void testNotifyOfOfferAppStatusSetToStageTrouve_doesNothingWhenStatusNotStageTrouve() throws IdDoesNotExistException {
        OfferApplication offerApplication = getDummyOfferApplication();
        when(offerApplicationService.getOneById(any())).thenReturn(getDummyOfferApplication());

        notificationService.notifyOfOfferAppStatusSetToStageTrouve(offerApplication);

        verify(notificationRepository, times(0)).save(any());
    }

    @Test
    void testNotifyOfOfferAppStatusSetToStageTrouve_cannotFindOfferApp() throws IdDoesNotExistException {
        when(offerApplicationService.getOneById(any())).thenThrow(IdDoesNotExistException.class);

        assertThrows(IdDoesNotExistException.class, () ->
                notificationService.notifyOfOfferAppStatusSetToStageTrouve(getDummyOfferApp()));
    }

    @Test
    void testNotifyOfContractSignature_signedByManager() throws IdDoesNotExistException {
        Contract contract = getDummyContract();
        contract.setManagerSignature("Signature Test");
        when(contractService.getOneById(any())).thenReturn(getDummyContract());

        notificationService.notifyOfContractSignature(contract);

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    void testNotifyOfContractSignature_signedByMonitor() throws IdDoesNotExistException {
        Contract contract = getDummyContract();
        contract.setMonitorSignature("Signature Test");
        when(contractService.getOneById(any())).thenReturn(getDummyContract());

        notificationService.notifyOfContractSignature(contract);

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    void testNotifyOfContractSignature_withNullParams() {
        assertThrows(IllegalArgumentException.class, () ->
                notificationService.notifyOfContractSignature(null));
    }

    @Test
    void testNotifyOfContractSignature_cannotFindContract() throws IdDoesNotExistException {
        when(contractService.getOneById(any())).thenThrow(IdDoesNotExistException.class);

        assertThrows(IdDoesNotExistException.class, () ->
                notificationService.notifyOfContractSignature(getDummyContract()));
    }

    @Test
    void testNotifyOfContractSignature_noChangeDoesNothing() throws IdDoesNotExistException {
        Contract contract = getDummyContract();
        when(contractService.getOneById(any())).thenReturn(contract);

        notificationService.notifyOfContractSignature(contract);

        verify(notificationRepository, times(0)).save(any());
    }

    @Test
    void testNotifyOfOfferAppInterviewSet() throws IdDoesNotExistException {
        OfferApplication offerApplication = getDummyOfferApplication();
        offerApplication.setInterviewDate(LocalDateTime.now());
        when(offerApplicationService.getOneById(any())).thenReturn(getDummyOfferApplication());

        notificationService.notifyOfOfferAppInterviewSet(offerApplication);

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    void testNotifyOfOfferAppInterviewSet_withNullParams() {
        assertThrows(IllegalArgumentException.class, () ->
                notificationService.notifyOfOfferAppInterviewSet(null));
    }

    @Test
    void testNotifyOfOfferAppInterviewSet_doesNothingWhenNoChange() throws IdDoesNotExistException {
        OfferApplication offerApplication = getDummyOfferApplication();
        when(offerApplicationService.getOneById(any())).thenReturn(offerApplication);

        notificationService.notifyOfOfferAppInterviewSet(offerApplication);

        verify(notificationRepository, times(0)).save(any());
    }

    @Test
    void testNotifyOfOfferAppInterviewSet_cannotFindOfferApp() throws IdDoesNotExistException {
        when(offerApplicationService.getOneById(any())).thenThrow(IdDoesNotExistException.class);

        assertThrows(IdDoesNotExistException.class, () ->
                notificationService.notifyOfOfferAppInterviewSet(getDummyOfferApp()));
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

    private List<Student> getDummyStudentList() {
        List<Student> dummyStudentList = new ArrayList<>();
        Long idIterator = 1L;
        for (int i = 0; i < 3; i++) {
            Student dummyStudent = getDummyStudent();
            dummyStudent.setId(idIterator);
            dummyStudentList.add(dummyStudent);
            idIterator++;
        }
        return dummyStudentList;
    }

    private Student getDummyStudent() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        dummyStudent.setLastName("Winter");
        dummyStudent.setFirstName("Summer");
        dummyStudent.setEmail("cant@outlook.com");
        dummyStudent.setPassword("cantPass");
        dummyStudent.setDepartment("info");
        dummyStudent.setMatricule("4673943");
        return dummyStudent;
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

    private Manager getDummyManager() {
        Manager dummyManager = new Manager();
        dummyManager.setPassword("Test1234");
        dummyManager.setEmail("oussamakably@gmail.com");
        dummyManager.setFirstName("Oussama");
        dummyManager.setLastName("Kably");
        dummyManager.setPhone("5143643320");
        return dummyManager;
    }

    private List<Manager> getDummyManagerList() {
        List<Manager> dummyManagerList = new ArrayList<>();
        for (long id = 0; id < 3; id++) {
            Manager dummyManager = getDummyManager();
            dummyManager.setId(id);
            dummyManagerList.add(dummyManager);
        }
        return dummyManagerList;
    }

    private Offer getDummyOffer() {
        Offer dummyOffer = new Offer();
        dummyOffer.setDepartment("Un departement");
        dummyOffer.setAddress("ajsaodas");
        dummyOffer.setId(1L);
        dummyOffer.setDescription("oeinoiendw");
        dummyOffer.setSalary(10);
        dummyOffer.setTitle("oeinoiendw");
        dummyOffer.setCreator(getDummyMonitor());
        return dummyOffer;
    }

    private Monitor getDummyMonitor() {
        Monitor dummyMonitor = new Monitor();
        dummyMonitor.setId(1L);
        dummyMonitor.setLastName("toto");
        dummyMonitor.setFirstName("titi");
        dummyMonitor.setEmail("toto@gmail.com");
        dummyMonitor.setPassword("testPassword");
        return dummyMonitor;
    }

    private OfferApplication getDummyOfferApp() {
        OfferApplication dummyOfferApplication = new OfferApplication();
        dummyOfferApplication.setOffer(getDummyOffer());
        dummyOfferApplication.setCurriculum(getDummyCurriculum());
        dummyOfferApplication.setId(1L);

        return dummyOfferApplication;
    }

}
