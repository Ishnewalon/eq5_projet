package com.gestionnaire_de_stage.service;


import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.repository.OfferRepository;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Optional;

@Service
public class OfferService{

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository){
        this.offerRepository = offerRepository;
    }

    public Offer mapToOffer(OfferDTO offerDTO){
        Offer offer = new Offer();
        offer.setAddress(offerDTO.getAddress());
        offer.setDepartment(offerDTO.getDepartment());
        offer.setDescription(offerDTO.getDescription());
        offer.setSalary(offerDTO.getSalary());
        offer.setTitle(offerDTO.getTitle());
        return offer;
    }

    public OfferDTO mapToOfferDTO(Offer o){
        OfferDTO dto = new OfferDTO();
        dto.setAddress(o.getAddress());
        if(o.getCreator() != null)
            dto.setCreator_id(o.getCreator().getId());
        dto.setDepartment(o.getDepartment());
        dto.setTitle(o.getTitle());
        dto.setDescription(o.getDescription());
        dto.setSalary(o.getSalary());
        return dto;
    }

    public Optional<Offer> create(Offer offer) throws ValidationException {
        if(offer == null)
            return Optional.empty();
        return Optional.of(offerRepository.save(offer));
    }

}