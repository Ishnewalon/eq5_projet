package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.OfferApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferApplicationServiceTest {
    @InjectMocks
    private OfferApplicationService offerApplicationService;
    @Mock
    private OfferService offerService;
    @Mock
    private StudentService studentService;
    @Mock
    private OfferApplicationRepository offerApplicationRepository;

    @Test
    void testCreate() throws StudentAlreadyAppliedToOfferException, IdDoesNotExistException {
        OfferApplication dummyOfferApplication = getDummyOfferApp();
        Student dummyStudent = getDummyStudent();
        Offer dummyOffer = getDummyOffer();
        when(offerApplicationRepository.save(any())).thenReturn(dummyOfferApplication);
        when(studentService.getOneByID(any())).thenReturn(dummyStudent);
        when(offerService.findOfferById(any())).thenReturn(Optional.of(dummyOffer));

        Optional<OfferApplication> actualOfferApplication = offerApplicationService.create(dummyOffer.getId(), dummyStudent.getId());

        assertThat(actualOfferApplication).isPresent();
        assertThat(actualOfferApplication.get()).isEqualTo(dummyOfferApplication);
    }

    @Test
    void testCreate_withStudentNonExistant() throws Exception {
        Offer dummyOffer = getDummyOffer();
        when(studentService.getOneByID(any())).thenThrow(IdDoesNotExistException.class);
        when(offerService.findOfferById(any())).thenReturn(Optional.of(getDummyOffer()));

        assertThrows(IdDoesNotExistException.class,
                () -> offerApplicationService.create(dummyOffer.getId(), 36L));
    }

    @Test
    void testCreate_withOfferNonExistant() throws Exception {
        Student dummyStudent = getDummyStudent();
        when(studentService.getOneByID(any())).thenReturn(dummyStudent);
        when(offerService.findOfferById(any())).thenReturn(Optional.empty());

        assertThrows(IdDoesNotExistException.class,
                () -> offerApplicationService.create(34L, dummyStudent.getId()));
    }

    @Test
    void testCreate_withIdOfferNull() {
        Offer dummyOffer = getDummyOffer();

        assertThrows(IllegalArgumentException.class,
                () -> offerApplicationService.create(null, dummyOffer.getId()));
    }

    @Test
    void testCreate_withIdCurriculumNull() {
        Student dummyStudent = getDummyStudent();

        assertThrows(IllegalArgumentException.class,
                () -> offerApplicationService.create(null, dummyStudent.getId()));
    }

    @Test
    void testCreate_withStudentAlreadyApplied() throws Exception {
        Offer dummyOffer = getDummyOffer();
        Student dummyStudent = getDummyStudent();
        when(studentService.getOneByID(any())).thenReturn(dummyStudent);
        when(offerService.findOfferById(any())).thenReturn(Optional.of(dummyOffer));
        when(offerApplicationRepository.existsByOfferAndStudent(any(), any())).thenReturn(true);

        assertThrows(StudentAlreadyAppliedToOfferException.class,
                () -> offerApplicationService.create(dummyOffer.getId(), dummyStudent.getId()));
    }


    private OfferApplication getDummyOfferApp() {
        OfferApplication offerApplicationDTO = new OfferApplication();
        offerApplicationDTO.setOffer(getDummyOffer());
        offerApplicationDTO.setStudent(getDummyStudent());
        offerApplicationDTO.setId(1L);

        return offerApplicationDTO;
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
}