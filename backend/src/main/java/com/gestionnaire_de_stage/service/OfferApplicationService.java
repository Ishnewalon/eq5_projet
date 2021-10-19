package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.repository.OfferApplicationRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OfferApplicationService {


    private final OfferApplicationRepository offerApplicationRepository;
    private final CurriculumService curriculumService;
    private final OfferService offerService;

    public OfferApplicationService(OfferApplicationRepository offerApplicationRepository, CurriculumService curriculumService, OfferService offerService) {
        this.offerApplicationRepository = offerApplicationRepository;
        this.curriculumService = curriculumService;
        this.offerService = offerService;
    }


    public Optional<OfferApplication> create(Long idOffer, Long idCurriculum) throws StudentAlreadyAppliedToOfferException, IdDoesNotExistException, IllegalArgumentException {
        Assert.isTrue(idOffer != null, "Erreur: Le id de l'offre ne peut pas être null");
        Assert.isTrue(idCurriculum != null, "Erreur: Le id du curriculum ne peut pas être null");
        Optional<Offer> offer = offerService.findOfferById(idOffer);
        Curriculum curriculum = curriculumService.getOneByID(idCurriculum);

        if (offer.isEmpty() || curriculum == null) throw new IdDoesNotExistException();
        if (offerApplicationRepository.existsByOfferAndCurriculum(offer.get(), curriculum))
            throw new StudentAlreadyAppliedToOfferException();

        OfferApplication offerApplication = new OfferApplication();
        offerApplication.setOffer(offer.get());
        offerApplication.setCurriculum(curriculum);

        return Optional.of(offerApplicationRepository.save(offerApplication));
    }
}
