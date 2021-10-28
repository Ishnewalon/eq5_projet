package com.gestionnaire_de_stage.service;


import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.OfferAlreadyExistsException;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.repository.OfferRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Offer mapToOffer(OfferDTO dto) {
        if (dto == null)
            return null;

        Offer offer = new Offer();
        offer.setAddress(dto.getAddress());
        offer.setDepartment(dto.getDepartment());
        offer.setDescription(dto.getDescription());
        offer.setSalary(dto.getSalary());
        offer.setTitle(dto.getTitle());
        return offer;
    }

    public OfferDTO mapToOfferDTO(Offer offer) {
        if (offer == null)
            return null;

        Assert.isTrue(offer.creatorEmail() != null, "Le courriel de l'utilisateur ne peut être null");

        OfferDTO dto = new OfferDTO();
        dto.setAddress(offer.getAddress());
        dto.setCreator_email(offer.getCreator().getEmail());
        dto.setDepartment(offer.getDepartment());
        dto.setTitle(offer.getTitle());
        dto.setDescription(offer.getDescription());
        dto.setSalary(offer.getSalary());
        return dto;
    }

    public List<OfferDTO> mapArrayToOfferDTO(List<Offer> offers) {
        return offers.stream().map(this::mapToOfferDTO).collect(Collectors.toList());
    }

    public Offer create(Offer offer) throws IllegalArgumentException, OfferAlreadyExistsException {
        Assert.isTrue(offer != null, "Offre est null");
        if (offerRepository.findOne(Example.of(offer)).isPresent())
            throw new OfferAlreadyExistsException();
        return offerRepository.save(offer);
    }

    public List<Offer> getOffersByDepartment(String department) {
        return offerRepository.findAllByDepartmentIgnoreCaseAndValidIsTrue(department);
    }

    public Offer update(Offer offer) throws IdDoesNotExistException, IllegalArgumentException {
        Assert.isTrue(offer != null, "offre est null");
        Assert.isTrue(offer.getId() != null, "L'id est null");
        if (!offerRepository.existsById(offer.getId()))
            throw new IdDoesNotExistException();
        return offerRepository.save(offer);
    }


    public Optional<Offer> findOfferById(Long idOffer) {
        return offerRepository.findById(idOffer);
    }

    public List<Offer> getValidOffers() {
        return offerRepository.findAllByValid(true);
    }

    public List<Offer> getNotValidatedOffers() {
        return offerRepository.findAllByValidIsNull();
    }
}
