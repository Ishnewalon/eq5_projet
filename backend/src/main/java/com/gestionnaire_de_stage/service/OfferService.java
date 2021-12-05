package com.gestionnaire_de_stage.service;


import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.dto.ValidationOffer;
import com.gestionnaire_de_stage.enums.TypeSession;
import com.gestionnaire_de_stage.exception.DoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.OfferAlreadyExistsException;
import com.gestionnaire_de_stage.exception.OfferAlreadyTreatedException;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.model.Session;
import com.gestionnaire_de_stage.repository.OfferApplicationRepository;
import com.gestionnaire_de_stage.repository.OfferRepository;
import com.gestionnaire_de_stage.repository.StudentRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final MonitorService monitorService;
    private final SessionService sessionService;
    private final StudentRepository studentRepository;
    private final OfferApplicationRepository offerApplicationRepository;
    private final Clock clock;

    public OfferService(OfferRepository offerRepository, MonitorService monitorService, SessionService sessionService, StudentRepository studentRepository, OfferApplicationRepository offerApplicationRepository, @Lazy Clock clock) {
        this.offerRepository = offerRepository;
        this.monitorService = monitorService;
        this.sessionService = sessionService;
        this.studentRepository = studentRepository;
        this.offerApplicationRepository = offerApplicationRepository;
        this.clock = clock;
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
        offer.setHoraireTravail(dto.getHoraireTravail());
        offer.setNbHeureSemaine(dto.getNbHeureSemaine());
        offer.setNbSemaine(dto.getNbSemaine());
        return offer;
    }

    public OfferDTO mapToOfferDTO(Offer offer) {
        if (offer == null)
            return null;

        Assert.isTrue(offer.creatorEmail() != null, "Le courriel du moniteur ne peut être vide");

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

    public Offer create(OfferDTO offerDto) throws IllegalArgumentException, OfferAlreadyExistsException, IdDoesNotExistException, DoesNotExistException {
        Assert.isTrue(offerDto != null, "L'offre ne peut pas être vide");
        Offer offer = mapToOffer(offerDto);
        if (offerRepository.findOne(Example.of(offer)).isPresent())
            throw new OfferAlreadyExistsException("Cette offre a déjà été créée");

        Monitor monitor = monitorService.getOneByEmail(offerDto.getCreator_email());
        offer.setCreator(monitor);

        Session session = sessionService.getOneBySessionId(offerDto.getIdSession());
        offer.setSession(session);

        return offerRepository.save(offer);
    }

    private void removeOffersOfWinter(List<Offer> offers) {
        offers.removeIf(offer -> {
            Session session = offer.getSession();
            return session.getYear().equals(Year.now(clock)) &&
                    session.getTypeSession() == TypeSession.HIVER;
        });
    }

    public Optional<Offer> findOfferById(Long idOffer) {
        return offerRepository.findById(idOffer);
    }

    public List<Offer> getValidOffers() {
        int monthValue = LocalDate.now(clock).getMonthValue();

        if (monthValue >= Month.SEPTEMBER.getValue())
            return offerRepository.findAllByValidAndSession_YearGreaterThanEqual(true, Year.now().plusYears(1));

        List<Offer> offers = offerRepository.findAllByValidAndSession_YearGreaterThanEqual(true, Year.now());

        if (monthValue >= Month.MAY.getValue())
            removeOffersOfWinter(offers);

        return offers;
    }

    public List<Offer> getNotValidatedOffers() {
        return offerRepository.findAllByValidNull();
    }

    public Offer validation(ValidationOffer validationOffer) throws IdDoesNotExistException, OfferAlreadyTreatedException {
        Assert.isTrue(validationOffer.getId() != null, "L'identifiant de l'offre ne peut pas être vide");
        if (!offerRepository.existsById(validationOffer.getId())) {
            throw new IdDoesNotExistException("Il n'y a pas d'offre associée à cet identifiant");
        }
        if (offerRepository.existsByIdAndValidNotNull(validationOffer.getId())) {
            throw new OfferAlreadyTreatedException("Cete offre a déjà été traitée");
        }
        Offer offer = offerRepository.getById(validationOffer.getId());
        offer.setValid(validationOffer.isValid());

        return offerRepository.save(offer);
    }

    public List<Offer> getOffersNotYetApplied(Long studentId) throws IdDoesNotExistException {
        Assert.isTrue(studentId != null, "L'identifiant de l'étudiant ne peut pas être vide");
        if (!studentRepository.existsById(studentId)) {
            throw new IdDoesNotExistException("Il n'y a pas d'étudiant associé à cet identifiant");
        }
        List<OfferApplication> listOffersApplied = offerApplicationRepository.getAllByCurriculum_StudentId(studentId);
        int monthValue = LocalDate.now(clock).getMonthValue();
        List<Offer> collect = listOffersApplied.stream().map(OfferApplication::getOffer).collect(Collectors.toList());

        if (monthValue >= Month.SEPTEMBER.getValue()) {
            List<Offer> listOffers = offerRepository.findAllByValidIsTrueAndSession_YearGreaterThanEqual(Year.now().plusYears(1));
            listOffers.removeAll(collect);
            return listOffers;
        }

        List<Offer> listOffers = offerRepository.findAllByValidIsTrueAndSession_YearGreaterThanEqual(Year.now());

        if (monthValue >= Month.MAY.getValue())
            removeOffersOfWinter(listOffers);

        listOffers.removeAll(collect);
        return listOffers;
    }
}
