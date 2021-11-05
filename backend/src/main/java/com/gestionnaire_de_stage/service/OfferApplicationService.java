package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.UpdateStatusDTO;
import com.gestionnaire_de_stage.enums.Status;
import com.gestionnaire_de_stage.exception.EmailDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyAppliedToOfferException;
import com.gestionnaire_de_stage.exception.StudentHasNoCurriculumException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.OfferApplicationRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

@Service
public class OfferApplicationService {

    private final OfferApplicationRepository offerApplicationRepository;
    private final OfferService offerService;
    private final StudentService studentService;


    public OfferApplicationService(OfferApplicationRepository offerApplicationRepository, OfferService offerService, StudentService studentService) {
        this.offerApplicationRepository = offerApplicationRepository;
        this.offerService = offerService;
        this.studentService = studentService;
    }


    public OfferApplication create(Long idOffer, Long idStudent) throws StudentAlreadyAppliedToOfferException, IdDoesNotExistException, IllegalArgumentException, StudentHasNoCurriculumException {
        Assert.isTrue(idOffer != null, "L'id de l'offre ne peut pas être null");
        Optional<Offer> offer = offerService.findOfferById(idOffer);
        Student student = studentService.getOneByID(idStudent);
        Curriculum curriculum = student.getPrincipalCurriculum();

        if (curriculum == null)
            throw new StudentHasNoCurriculumException();

        if (offer.isEmpty())
            throw new IdDoesNotExistException();

        if (offerApplicationRepository.existsByOfferAndCurriculum(offer.get(), curriculum))
            throw new StudentAlreadyAppliedToOfferException();

        OfferApplication offerApplication = new OfferApplication();
        offerApplication.setOffer(offer.get());
        offerApplication.setCurriculum(student.getPrincipalCurriculum());
        offerApplication.setStatus(Status.CV_ENVOYE);

        return offerApplicationRepository.save(offerApplication);
    }

    public List<OfferApplication> getAllByOfferCreatorEmail(String email) {
        Assert.isTrue(email != null, "Le courriel ne peut pas être null");
        return offerApplicationRepository.getAllByOffer_CreatorEmail(email);
    }

    public List<OfferApplication> getAllOffersStudentApplied(Long idStudent) throws IdDoesNotExistException, IllegalArgumentException {
        Assert.isTrue(idStudent != null, "L'id de l'étudiant ne peut pas être null");
        if (studentService.getOneByID(idStudent) == null)
            throw new IdDoesNotExistException();
        return offerApplicationRepository.getAllByStatusAndCurriculum_StudentId(Status.EN_ATTENTE_REPONSE, idStudent);
    }

    public boolean updateStatus(UpdateStatusDTO updateStatusDTO) throws IdDoesNotExistException {
        Assert.isTrue(updateStatusDTO.getIdOfferApplied() != null, "L'id de l'offre ne peut pas être null");
        OfferApplication offerApplication = offerApplicationRepository.getById(updateStatusDTO.getIdOfferApplied());
        if (updateStatusDTO.getIsAccepted()) {
            offerApplication.setStatus(Status.STAGE_TROUVE);
        } else {
            offerApplication.setStatus(Status.STAGE_REFUSER);
        }
        offerApplicationRepository.save(offerApplication);
        return updateStatusDTO.getIsAccepted();
    }
}
