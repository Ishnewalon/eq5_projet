package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.OfferApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
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
    private CurriculumService curriculumService;
    @Mock
    private OfferApplicationRepository offerApplicationRepository;

    @Test
    void testCreate() throws StudentAlreadyAppliedToOfferException, IdDoesNotExistException {
        OfferApplication dummyOfferApplication = getDummyOfferApp();
        Curriculum dummyCurriculum = getDummyCurriculum();
        Offer dummyOffer = getDummyOffer();
        when(offerApplicationRepository.save(any())).thenReturn(dummyOfferApplication);
        when(curriculumService.getOneByID(any())).thenReturn(dummyCurriculum);
        when(offerService.findOfferById(any())).thenReturn(Optional.of(dummyOffer));

        Optional<OfferApplication> actualOfferApplication = offerApplicationService.create(dummyOffer.getId(), dummyCurriculum.getId());

        assertThat(actualOfferApplication).isPresent();
        assertThat(actualOfferApplication.get()).isEqualTo(dummyOfferApplication);
    }

    @Test
    void testCreate_withCurriculumNonExistant() throws Exception {
        Offer dummyOffer = getDummyOffer();
        when(curriculumService.getOneByID(any())).thenReturn(null);
        when(offerService.findOfferById(any())).thenReturn(Optional.of(getDummyOffer()));

        assertThrows(IdDoesNotExistException.class,
                () -> offerApplicationService.create(dummyOffer.getId(), 36L));
    }

    @Test
    void testCreate_withOfferNonExistant() throws Exception {
        Curriculum dummyCurriculum = getDummyCurriculum();
        when(curriculumService.getOneByID(any())).thenReturn(dummyCurriculum);
        when(offerService.findOfferById(any())).thenReturn(Optional.empty());

        assertThrows(IdDoesNotExistException.class,
                () -> offerApplicationService.create(34L, dummyCurriculum.getId()));
    }

    @Test
    void testCreate_withIdOfferNull() {
        Offer dummyOffer = getDummyOffer();

        assertThrows(IllegalArgumentException.class,
                () -> offerApplicationService.create(dummyOffer.getId(), null));
    }

    @Test
    void testCreate_withIdCurriculumNull() {
        Curriculum dummyCurriculum = getDummyCurriculum();

        assertThrows(IllegalArgumentException.class,
                () -> offerApplicationService.create(null, dummyCurriculum.getId()));
    }

    @Test
    void testCreate_withStudentAlreadyApplied() throws Exception {
        Offer dummyOffer = getDummyOffer();
        Curriculum dummyCurriculum = getDummyCurriculum();
        when(curriculumService.getOneByID(any())).thenReturn(dummyCurriculum);
        when(offerService.findOfferById(any())).thenReturn(Optional.of(dummyOffer));
        when(offerApplicationRepository.existsByOfferAndCurriculum(any(), any())).thenReturn(true);

        assertThrows(StudentAlreadyAppliedToOfferException.class,
                () -> offerApplicationService.create(dummyOffer.getId(), dummyCurriculum.getId()));
    }

   /* @Test
    void testGetAllByOfferCreatorEmail_withValidEntries() throws EmailDoesNotExistException {
        List<OfferApplication> offerApplicationList = getDummyOfferAppList();
        String email = "americanm@email.com";
        when(offerApplicationRepository.existsByOffer_CreatorEmail(any()))
                .thenReturn(true);
        when(offerApplicationRepository.getAllByOffer_CreatorEmail(any()))
                .thenReturn(getDummyOfferAppList());

        List<OfferApplication> actualOfferAppList = offerApplicationService.getAllByOfferCreatorEmail(email);

        assertThat(actualOfferAppList.size()).isEqualTo(offerApplicationList.size());
    }

    @Test
    void testGetAllByOfferCreatorEmail_withNullEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> offerApplicationService.getAllByOfferCreatorEmail(null));
    }

    @Test
    void testGetAllByOfferCreatorEmail_withInvalidEmail() {
        String email = "fredmerc@email.com";

        assertThrows(EmailDoesNotExistException.class,
                () -> offerApplicationService.getAllByOfferCreatorEmail(email));
    }*/

    private OfferApplication getDummyOfferApp() {
        OfferApplication offerApplicationDTO = new OfferApplication();
        offerApplicationDTO.setOffer(getDummyOffer());
        offerApplicationDTO.setCurriculum(getDummyCurriculum());
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

    private Curriculum getDummyCurriculum() {
        Curriculum dummyCurriculum = new Curriculum();

        dummyCurriculum.setId(1L);
        dummyCurriculum.setData("some xml".getBytes());
        dummyCurriculum.setName("fileeeename");
        dummyCurriculum.setStudent(new Student());
        return dummyCurriculum;
    }

    private List<OfferApplication> getDummyOfferAppList() {
        List<OfferApplication> offerApplicationList = new ArrayList<>();
        OfferApplication dummyOfferApplicationDTO = new OfferApplication();
        dummyOfferApplicationDTO.setOffer(getDummyOffer());
        dummyOfferApplicationDTO.setCurriculum(getDummyCurriculum());
        dummyOfferApplicationDTO.setId(1L);
        offerApplicationList.add(dummyOfferApplicationDTO);

        dummyOfferApplicationDTO.setId(2L);
        offerApplicationList.add(dummyOfferApplicationDTO);

        dummyOfferApplicationDTO.setId(3L);
        offerApplicationList.add(dummyOfferApplicationDTO);

        return offerApplicationList;
    }
}