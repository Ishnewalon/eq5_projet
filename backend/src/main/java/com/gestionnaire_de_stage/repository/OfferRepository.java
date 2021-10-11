package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByDepartment(String department);


}
