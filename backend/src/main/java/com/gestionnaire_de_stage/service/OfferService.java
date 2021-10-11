package com.gestionnaire_de_stage.service;


import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.OfferRepository;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Offer mapToOffer(OfferDTO offerDTO) {
        Offer offer = new Offer();
        offer.setAddress(offerDTO.getAddress());
        offer.setDepartment(offerDTO.getDepartment());
        offer.setDescription(offerDTO.getDescription());
        offer.setSalary(offerDTO.getSalary());
        offer.setTitle(offerDTO.getTitle());
        return offer;
    }

    public OfferDTO mapToOfferDTO(Offer o) {
        OfferDTO dto = new OfferDTO();
        dto.setAddress(o.getAddress());
        if (o.getCreator() != null)
            dto.setCreator_id(o.getCreator().getId());
        dto.setDepartment(o.getDepartment());
        dto.setTitle(o.getTitle());
        dto.setDescription(o.getDescription());
        dto.setSalary(o.getSalary());
        return dto;
    }

    public List<OfferDTO> mapArrayToOfferDTO(List<Offer> offers) {
        return offers.stream().map(this::mapToOfferDTO).collect(Collectors.toList());
    }

    public Optional<Offer> create(Offer offer) throws ValidationException {
        if (offer == null)
            return Optional.empty();
        return Optional.of(offerRepository.save(offer));
    }

    public List<Offer> getOffersByDepartment(String department) {
        return offerRepository.findAllByDepartment(department);
    }


    public Optional<Offer> update(Offer offer) {
        if (offer != null && offerRepository.existsById(offer.getId()))
            return Optional.of(offerRepository.save(offer));
        return Optional.empty();
    }
}
