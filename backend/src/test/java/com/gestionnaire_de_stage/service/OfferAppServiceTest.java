package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApp;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.OfferAppRepository;
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
class OfferAppServiceTest {
    @InjectMocks
    private OfferAppService offerAppService;
    @Mock
    private OfferService offerService;
    @Mock
    private CurriculumService curriculumService;
    @Mock
    private OfferAppRepository offerAppRepository;

    @Test
    void testCreate() throws StudentAlreadyAppliedToOfferException, IdDoesNotExistException {
        OfferApp dummyOfferApp = getDummyOfferApp();
        Curriculum dummyCurriculum = getDummyCurriculum();
        Offer dummyOffer = getDummyOffer();
        when(offerAppRepository.save(any())).thenReturn(dummyOfferApp);
        when(curriculumService.getCurriculum(any())).thenReturn(dummyCurriculum);
        when(offerService.findOfferById(any())).thenReturn(Optional.of(dummyOffer));

        Optional<OfferApp> offerApp = offerAppService.create(1L, 1L);

        assertThat(offerApp).isPresent();
        assertThat(offerApp.get()).isEqualTo(dummyOfferApp);
    }

    @Test
    void testCreate_withNoCurriculum() {
        Offer dummyOffer = getDummyOffer();
        when(curriculumService.getCurriculum(any())).thenReturn(null);
        when(offerService.findOfferById(any())).thenReturn(Optional.of(getDummyOffer()));

        assertThrows(IdDoesNotExistException.class, () -> offerAppService.create(dummyOffer.getId(), null));
    }

    @Test
    void testCreate_withNoOffer() {
        Offer dummyOffer = getDummyOffer();
        when(curriculumService.getCurriculum(any())).thenReturn(getDummyCurriculum());
        when(offerService.findOfferById(any())).thenReturn(Optional.empty());

        assertThrows(IdDoesNotExistException.class, () -> offerAppService.create(dummyOffer.getId(), null));
    }

    @Test
    void testCreate_withStudentAlreadyApplied() {
        Offer dummyOffer = getDummyOffer();
        when(curriculumService.getCurriculum(any())).thenReturn(getDummyCurriculum());
        when(offerService.findOfferById(any())).thenReturn(Optional.of(dummyOffer));
        when(offerAppRepository.existsByOfferAndCurriculum(any(), any())).thenReturn(true);

        assertThrows(StudentAlreadyAppliedToOfferException.class, () -> offerAppService.create(dummyOffer.getId(), null));
    }

    private OfferApp getDummyOfferApp() {
        OfferApp offerAppDTO = new OfferApp();
        offerAppDTO.setOffer(getDummyOffer());
        offerAppDTO.setCurriculum(getDummyCurriculum());
        offerAppDTO.setId(1L);

        return offerAppDTO;
    }

    private Offer getDummyOffer() {
        Offer offer = new Offer();
        offer.setDepartment("Un departement");
        offer.setAddress("ajsaodas");
        offer.setId(1L);
        offer.setDescription("oeinoiendw");
        offer.setSalary(10);
        offer.setTitle("oeinoiendw");
        return offer;
    }

    private Curriculum getDummyCurriculum() {
        Curriculum curriculum = new Curriculum();

        curriculum.setId(1L);
        curriculum.setData("some xml".getBytes());
        curriculum.setName("fileeeename");
        curriculum.setStudent(new Student());
        return curriculum;
    }
}