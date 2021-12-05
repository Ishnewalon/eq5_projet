package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByValidIsTrueAndSession_YearGreaterThanEqual(Year year);

    List<Offer> findAllByValidAndSession_YearGreaterThanEqual(Boolean valid, Year year);

    List<Offer> findAllByValidNull();

    boolean existsByIdAndValidNotNull(Long id);
}
