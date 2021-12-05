package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NotificationService {

    private final CurriculumService curriculumService;

    private final OfferService offerService;

    private final ManagerService managerService;

    private final StudentService studentService;

    private final OfferApplicationService offerApplicationService;

    private final ContractService contractService;

    private final NotificationRepository notificationRepository;

    public NotificationService(CurriculumService curriculumService, OfferService offerService, NotificationRepository notificationRepository, ManagerService managerService, StudentService studentService, OfferApplicationService offerApplicationService, ContractService contractService) {
        this.curriculumService = curriculumService;
        this.offerService = offerService;
        this.notificationRepository = notificationRepository;
        this.managerService = managerService;
        this.studentService = studentService;
        this.offerApplicationService = offerApplicationService;
        this.contractService = contractService;
    }

    public List<Notification> getAllByUserId(Long userId) throws IllegalArgumentException {
        Assert.notNull(userId, "Le userId ne peut pas être vide");

        return notificationRepository.findAllBySeenIsFalseAndTargetedUser_IdOrderByCreatedDateDesc(userId);
    }

    public Notification updateSeen(Long notificationId) throws IdDoesNotExistException {
        Assert.notNull(notificationId, "Le notificationId ne peut pas être vide");

        if (!notificationRepository.existsById(notificationId))
            throw new IdDoesNotExistException("Aucune notification trouvée avec cet ID");

        Notification notification = notificationRepository.getById(notificationId);
        notification.setSeen(true);
        return notificationRepository.save(notification);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifyOfCurriculumValidation(Curriculum curriculum) throws IdDoesNotExistException, IllegalArgumentException {
        Assert.notNull(curriculum, "Le curriculum ne peut pas être vide");
        Curriculum previous = curriculumService.getOneById(curriculum.getId());

        if (curriculum.getIsValid() != previous.getIsValid()) {
            notificationRepository.save(new Notification(
                    curriculum.getStudent(),
                    "Votre curriculum : " + curriculum.getName() + " à été vérifié. Veuillez voir son état dans vos Curriculums."
            ));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifyOfNewApplicant(OfferApplication offerApplication) throws IllegalArgumentException {
        Assert.notNull(offerApplication, "Le offerApplication ne peut pas être vide");

        Offer offer = offerApplication.getOffer();
        notificationRepository.save(new Notification(
                offer.getCreator(),
                "Votre offre de stage à reçue un nouvel appliquant."
        ));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifyOfOfferAppStatusSetToStageTrouve(OfferApplication offerApplication) throws IdDoesNotExistException, IllegalArgumentException {
        Assert.notNull(offerApplication, "Le offerApplication ne peut pas être vide");
        OfferApplication previous = offerApplicationService.getOneById(offerApplication.getId());

        if (offerApplication.getStatus() != previous.getStatus() && offerApplication.getStatus() == Status.STAGE_TROUVE) {
            List<Manager> managers = managerService.getAll();
            managers.forEach(manager ->
                    notificationRepository.save(new Notification(
                            manager,
                            "Un étudiant est prêt pour débuter la signature de contrat."
                    )));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifyOfOfferAppInterviewSet(OfferApplication offerApplication) throws IdDoesNotExistException, IllegalArgumentException {
        Assert.notNull(offerApplication, "Le offerApplication ne peut pas être vide");
        OfferApplication previous = offerApplicationService.getOneById(offerApplication.getId());

        if (offerApplication.getInterviewDate() != previous.getInterviewDate()) {
            Curriculum curriculum = offerApplication.getCurriculum();
            Student student = curriculum.getStudent();
            Offer offer = offerApplication.getOffer();
            notificationRepository.save(new Notification(
                    offer.getCreator(),
                    "N'oubliez pas votre entrevue avec " + student.getLastName() +
                            ", " + student.getFirstName() + " le " +
                            offerApplication.getInterviewDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")) + "."
            ));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifyOfNewOffer(Offer offer) throws IllegalArgumentException {
        Assert.notNull(offer, "Le offer ne peut pas être vide");

        List<Manager> managers = managerService.getAll();
        managers.forEach(manager ->
                notificationRepository.save(new Notification(
                        manager,
                        "Une nouvelle offre est en attente de validation."
                )));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifyOfOfferValidation(Offer offer) throws IllegalArgumentException {
        Assert.notNull(offer, "Le offre ne peut pas être vide");
        Optional<Offer> previous = offerService.findOfferById(offer.getId());

        if (previous.isPresent() && offer.getValid() != previous.get().getValid()) {
            notificationRepository.save(new Notification(
                    offer.getCreator(),
                    "Votre offre de stage a été vérifiée."
            ));

            if (offer.getValid()) {
                List<Student> students = studentService.getAll();
                students.forEach(student ->
                        notificationRepository.save(new Notification(
                                student,
                                "Une nouvelle offre de stage a été publiée."
                        )));
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifyOfContractSignature(Contract contract) throws IdDoesNotExistException, IllegalArgumentException {
        Assert.notNull(contract, "Le contrat ne peut pas être vide");
        Contract previous = contractService.getOneById(contract.getId());

        if (!Objects.equals(contract.getManagerSignature(), previous.getManagerSignature())) {
            Student student = contract.getStudent();
            notificationRepository.save(new Notification(
                    contract.getMonitor(),
                    "Votre contrat avec l'étudiant " + student.getLastName() +
                            ", " + student.getFirstName() + " est prêt à être signé."
            ));
        }

        if (!Objects.equals(contract.getMonitorSignature(), previous.getMonitorSignature())) {
            Offer offer = contract.getOffer();
            notificationRepository.save(new Notification(
                    contract.getStudent(),
                    "Votre contrat pour le poste de " + offer.getTitle() +
                            " est prêt à être signé."
            ));
        }
    }
}
