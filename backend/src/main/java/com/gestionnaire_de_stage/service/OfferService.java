package com.gestionnaire_de_stage.service;


import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService implements ICrudService<Offer, Long>{

    @Autowired
    private OfferRepository offerRepository;


    public Offer mapToOffer(OfferDTO offerDTO){
        Offer offer = new Offer();
        offer.setAddress(offerDTO.getAddress());
        offer.setDepartment(offerDTO.getDepartment());
        offer.setDescription(offerDTO.getDescription());
        offer.setSalary(offerDTO.getSalary());
        offer.setTitle(offerDTO.getTitle());
        return offer;
    }

    @Override
    public Optional<Offer> create(Offer offer) throws ValidationException {
        return offer == null ? Optional.empty() : Optional.of(offerRepository.save(offer));
    }


    @Override
    public Optional<Offer> getOneByID(Long id) {
        if (id != null && offerRepository.existsById(id)) {
            return Optional.of(offerRepository.getById(id));
        }
        return Optional.empty();
    }

    @Override
    public List<Offer> getAll() {
        return offerRepository.findAll();
    }

    @Override
    public Optional<Offer> update(Offer offer, Long id) throws Exception {
        if (id != null && offerRepository.existsById(id) && offer != null) {
            offer.setId(id);
            return Optional.of(offerRepository.save(offer));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteByID(Long id) {
        if (id != null && offerRepository.existsById(id)) {
            offerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
