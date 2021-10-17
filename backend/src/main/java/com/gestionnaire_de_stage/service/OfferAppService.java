package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApp;
import com.gestionnaire_de_stage.repository.OfferAppRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@Service
public class OfferAppService {


    private final OfferAppRepository offerAppRepository;
    private final CurriculumService curriculumService;
    private final OfferService offerService;

    public OfferAppService(OfferAppRepository offerAppRepository, CurriculumService curriculumService, OfferService offerService) {
        this.offerAppRepository = offerAppRepository;
        this.curriculumService = curriculumService;
        this.offerService = offerService;
    }


    public Optional<OfferApp> create(Long idOffer, Long idCurriculum) throws StudentAlreadyAppliedToOfferException, IdDoesNotExistException, IllegalArgumentException {
        assertTrue(idOffer != null, "Erreur: Le id de l'offre ne peut pas etre null");
        assertTrue(idCurriculum != null, "Erreur: Le id du curriculum ne peut pas etre null");
        Optional<Offer> offer = offerService.findOfferById(idOffer);
        Curriculum curriculum = curriculumService.getCurriculum(idCurriculum);

        if (offer.isEmpty() || curriculum == null) throw new IdDoesNotExistException();
        if (offerAppRepository.existsByOfferAndCurriculum(offer.get(), curriculum))
            throw new StudentAlreadyAppliedToOfferException();

        OfferApp offerApp = new OfferApp();
        offerApp.setOffer(offer.get());
        offerApp.setCurriculum(curriculum);

        return Optional.of(offerAppRepository.save(offerApp));
    }
}
