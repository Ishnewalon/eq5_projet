package com.gestionnaire_de_stage.service;


import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.dto.ValidationOffer;
import com.gestionnaire_de_stage.exception.*;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.Session;
import com.gestionnaire_de_stage.repository.OfferRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final MonitorService monitorService;
    private final SessionService sessionService;

    public OfferService(OfferRepository offerRepository, MonitorService monitorService, SessionService sessionService) {
        this.offerRepository = offerRepository;
        this.monitorService = monitorService;
        this.sessionService = sessionService;
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
        offer.setDateDebut(dto.getDateDebut());
        offer.setDateFin(dto.getDateFin());
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

    public Offer create(OfferDTO offerDto) throws IllegalArgumentException, OfferAlreadyExistsException, EmailDoesNotExistException, SessionDoesNotExistException {
        Assert.isTrue(offerDto != null, "Offre est null");
        Offer offer = mapToOffer(offerDto);
        if (offerRepository.findOne(Example.of(offer)).isPresent())
            throw new OfferAlreadyExistsException();

        Monitor monitor = monitorService.getOneByEmail(offerDto.getCreator_email());
        offer.setCreator(monitor);
        return offerRepository.save(offer);
    }

    public List<Offer> getOffersByDepartment(String department) throws IllegalArgumentException {
        Assert.isTrue(department != null, "Le département est null ou vide");
        return offerRepository.findAllByDepartmentIgnoreCaseAndValidIsTrue(department);
    }

    public Optional<Offer> findOfferById(Long idOffer) {
        return offerRepository.findById(idOffer);
    }

    public List<Offer> getValidOffers() {
        return offerRepository.findAllByValid(true);
    }

    public List<Offer> getNotValidatedOffers() {
        return offerRepository.findAllByValidNull();
    }

    public Offer validation(ValidationOffer validationOffer) throws IdDoesNotExistException, OfferAlreadyTreatedException {
        Assert.isTrue(validationOffer.getId() != null, "L'id est null");
        if (!offerRepository.existsById(validationOffer.getId())) throw new IdDoesNotExistException();
        if (offerRepository.existsByIdAndValidNotNull(validationOffer.getId()))
            throw new OfferAlreadyTreatedException();

        Offer offer = offerRepository.getById(validationOffer.getId());
        offer.setValid(validationOffer.isValid());

        return offerRepository.save(offer);
    }

}
