package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.UpdateStatusDTO;
import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.exception.DateNotValidException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.exception.StudentHasNoCurriculumException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.OfferApplicationRepository;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferApplicationService {

    private final OfferApplicationRepository offerApplicationRepository;
    private final OfferService offerService;
    private final ManagerService managerService;
    private final StudentService studentService;
    private final SupervisorRepository supervisorRepository;


    public OfferApplicationService(OfferApplicationRepository offerApplicationRepository, OfferService offerService, ManagerService managerService, StudentService studentService, SupervisorRepository supervisorRepository) {
        this.offerApplicationRepository = offerApplicationRepository;
        this.offerService = offerService;
        this.managerService = managerService;
        this.studentService = studentService;
        this.supervisorRepository = supervisorRepository;
    }

    public OfferApplication create(Long idOffer, Long idStudent) throws StudentAlreadyAppliedToOfferException, IdDoesNotExistException, IllegalArgumentException, StudentHasNoCurriculumException {
        Assert.isTrue(idOffer != null, "L'identifiant de l'offre ne peut pas être vide");
        Optional<Offer> offer = offerService.findOfferById(idOffer);
        Student student = studentService.getOneByID(idStudent);
        Curriculum curriculum = student.getPrincipalCurriculum();

        if (curriculum == null)
            throw new StudentHasNoCurriculumException("Vous devez avoir un curriculum principal valide avant de postuler");

        if (offer.isEmpty())
            throw new IdDoesNotExistException("Il n'y a pas d'offre associé à cet identifiant");

        if (offerApplicationRepository.existsByOfferAndCurriculum(offer.get(), curriculum))
            throw new StudentAlreadyAppliedToOfferException("Vous avez déjà postulé sur cette offre");

        OfferApplication offerApplication = new OfferApplication();
        offerApplication.setOffer(offer.get());
        offerApplication.setCurriculum(student.getPrincipalCurriculum());
        offerApplication.setStatus(Status.CV_ENVOYE);
        offerApplication.setSession(offer.get().getSession());

        return offerApplicationRepository.save(offerApplication);
    }

    public List<OfferApplication> getAllByOfferCreatorEmail(String email) {
        Assert.isTrue(email != null, "Le courriel ne peut pas être vide");
        return offerApplicationRepository.getAllByOffer_CreatorEmail(email);
    }

    public List<OfferApplication> getAllByOfferStatusAndStudentID(Status status, Long studentID) throws IllegalArgumentException {
        Assert.isTrue(studentID != null, "L'identifiant de l'étudiant ne peut pas être vide");
        Assert.isTrue(status != null, "Le statut de l'offre ne peut pas être vide");

        return offerApplicationRepository.getAllByStatusAndCurriculum_StudentIdAndSession_YearGreaterThanEqual(status, studentID, Year.now());
    }

    public OfferApplication setInterviewDate(Long offerAppID, LocalDateTime date) throws IdDoesNotExistException, DateNotValidException, IllegalArgumentException {
        Assert.isTrue(offerAppID != null, "L'identifiant de l'offre ne peut pas être vide");
        Assert.isTrue(date != null, "La date ne peut pas être vide");

        if (!offerApplicationRepository.existsById(offerAppID))
            throw new IdDoesNotExistException("Il n'y a pas d'offre associé à cet identifiant");

        if (isDateInvalid(date))
            throw new DateNotValidException("La date choisie est invalide");

        OfferApplication offerApplication = offerApplicationRepository.getById(offerAppID);
        offerApplication.setStatus(Status.EN_ATTENTE_ENTREVUE);
        offerApplication.setInterviewDate(date);

        return offerApplicationRepository.save(offerApplication);
    }

    private boolean isDateInvalid(LocalDateTime date) {
        return !date.isAfter(LocalDateTime.now()) ||
                !date.isBefore(LocalDateTime.now().plusMonths(2));
    }

    public List<OfferApplication> getOffersApplicationsStageTrouverManagerId(Long id) throws IdDoesNotExistException {//TODO combine with getAllOffersStudentApplied
        Assert.isTrue(id != null, "L'identifiant du gestionnaire ne peut pas être vide");
        if (managerService.isIDNotValid(id))
            throw new IdDoesNotExistException("Il n'y a pas de gestionnaire associé à cet identifiant");

        return offerApplicationRepository.getAllByStatusAndSession_YearGreaterThanEqual(Status.STAGE_TROUVE, Year.now());
    }

    public List<Student> getOffersApplicationsStageTrouver() {
        List<OfferApplication> offerApplicationList = offerApplicationRepository
                .getAllByStatusAndSession_YearGreaterThanEqualAndCurriculum_Student_SupervisorIsNull(Status.STAGE_TROUVE, Year.now());

        return offerApplicationList.stream()
                .map(offerApplication -> getStudent(offerApplication.getCurriculum())).distinct().collect(Collectors.toList());
    }

    private Student getStudent(Curriculum curriculum) {
        return curriculum != null ? curriculum.getStudent() : null;
    }

    public OfferApplication getOneById(Long idOfferApplication) throws IdDoesNotExistException {
        Assert.isTrue(idOfferApplication != null, "L'identifiant de l'application ne peut pas être vide");
        if (!offerApplicationRepository.existsById(idOfferApplication))
            throw new IdDoesNotExistException("L'identifiant de l'application ne peut pas être vide");
        return offerApplicationRepository.getById(idOfferApplication);
    }

    public List<OfferApplication> getAllOffersStudentAppliedAndStatusWaiting(Long idStudent) throws IdDoesNotExistException, IllegalArgumentException {
        Assert.isTrue(idStudent != null, "L'identifiant de l'étudiant ne peut pas être vide");
        if (studentService.getOneByID(idStudent) == null)
            throw new IdDoesNotExistException("Il n'y a pas d'étudiant associé à cet identifiant");
        return offerApplicationRepository.getAllByStatusAndCurriculum_StudentIdAndSession_YearGreaterThanEqual(Status.EN_ATTENTE_REPONSE, idStudent, Year.now());
    }

    public List<OfferApplication> getAllOffersStudentApplied(Long idStudent) throws IdDoesNotExistException, IllegalArgumentException {
        Assert.isTrue(idStudent != null, "L'identifiant de l'étudiant ne peut pas être vide");
        if (studentService.getOneByID(idStudent) == null)
            throw new IdDoesNotExistException("Il n'y a pas d'étudiant associé à cet identifiant");
        return offerApplicationRepository.getAllByCurriculum_StudentId(idStudent);
    }

    public String updateStatus(UpdateStatusDTO updateStatusDTO) {
        Assert.isTrue(updateStatusDTO.getIdOfferApplied() != null, "L'identifiant de l'offre ne peut pas être vide");
        OfferApplication offerApplication = offerApplicationRepository.getById(updateStatusDTO.getIdOfferApplied());
        if (updateStatusDTO.isAccepted()) {
            offerApplication.setStatus(Status.STAGE_TROUVE);
        } else {
            offerApplication.setStatus(Status.STAGE_REFUSE);
        }
        offerApplicationRepository.save(offerApplication);
        return updateStatusDTO.isAccepted() ? "Statut changé, attendez la signature du contrat" : "Statut changé, stage refusé";
    }

    public int updateAllOfferApplicationThatWereInAInterviewStatusFromStatusToOther(Status status, Status newStatus) throws IllegalArgumentException {
        Assert.notNull(status, "Les deux statut ne peuvent pas être vide");
        Assert.notNull(newStatus, "Les deux statut ne peuvent pas être vide");
        Assert.isTrue(status != newStatus, "Les deux statut ne peuvent pas être identiques");

        return offerApplicationRepository.updateAllOfferApplicationThatWereInAInterviewStatusToStatus(status, newStatus, Year.now());
    }

    public List<OfferApplication> getAllBySupervisorId(Long supervisor_id) throws IdDoesNotExistException {
        Assert.isTrue(supervisor_id != null, "L'identifiant du superviseur ne peut pas être vide");
        if (!supervisorRepository.existsById(supervisor_id)) {
            throw new IdDoesNotExistException("Il n'y a pas de superviseur associé à cet identifiant");
        }
        return offerApplicationRepository.findAllByCurriculum_Student_Supervisor_IdAndSession_YearGreaterThanEqual(supervisor_id, Year.now());
    }

    public List<OfferApplication> getAllOffersApplication() {
        return offerApplicationRepository.findAll();
    }

    public boolean isCurriculumInUse(Curriculum curriculum) throws IllegalArgumentException {
        Assert.notNull(curriculum, "Le curriculum ne peut pas être vide");
        return offerApplicationRepository.existsByCurriculum(curriculum);
    }
}