package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferAppRepository extends JpaRepository<OfferApp, Long> {
    boolean existsByOfferAndCurriculum(Offer offer, Curriculum curriculum);
}
