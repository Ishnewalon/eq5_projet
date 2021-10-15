package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApp;
import com.gestionnaire_de_stage.repository.OfferAppRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OfferAppService {


    private final OfferAppRepository offerAppRepository;

    public OfferAppService(OfferAppRepository offerAppRepository) {
        this.offerAppRepository = offerAppRepository;
    }


    public Optional<OfferApp> create(Offer offer, Curriculum curriculum) {
        if (offer == null || curriculum == null)
            return Optional.empty();
        OfferApp offerApp = new OfferApp();
        offerApp.setOffer(offer);
        offerApp.setCurriculum(curriculum);
        return Optional.of(offerAppRepository.save(offerApp));
    }
}
