package com.gestionnaire_de_stage.service;


import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.MonitorAlreadyExistsException;
import com.gestionnaire_de_stage.exception.OfferAlreadyExistsException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.OfferRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Offer mapToOffer(OfferDTO o) {
        if(o == null)
            return null;

        Offer offer = new Offer();
        offer.setAddress(o.getAddress());
        offer.setDepartment(o.getDepartment());
        offer.setDescription(o.getDescription());
        offer.setSalary(o.getSalary());
        offer.setTitle(o.getTitle());
        return offer;
    }

    public OfferDTO mapToOfferDTO(Offer o) {
        if(o == null)
            return null;

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

    public Offer create(Offer offer) throws IllegalArgumentException, OfferAlreadyExistsException{
        Assert.isTrue(offer != null, "Offre est null");
        if(offerRepository.findOne(Example.of(offer)).isPresent())
            throw new OfferAlreadyExistsException();
        return offerRepository.save(offer);
    }

    public List<OfferDTO> getOffersByDepartment(String department) {
        return mapArrayToOfferDTO(offerRepository.findAllByDepartment(department));
    }

    public Offer update(Offer offer) throws IdDoesNotExistException, IllegalArgumentException{
        Assert.isTrue(offer != null, "offre est null");
        Assert.isTrue(offer.getId() != null, "L'id est null");
        if (!offerRepository.existsById(offer.getId()))
            throw new IdDoesNotExistException();
        return offerRepository.save(offer);
    }

    public List<Offer> getAll() {
        return offerRepository.findAll();
    }
}
