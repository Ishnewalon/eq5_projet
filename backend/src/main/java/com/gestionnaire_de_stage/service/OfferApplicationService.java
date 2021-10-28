package com.gestionnaire_de_stage.service;

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

import java.util.Optional;
import java.util.List;

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

        if(curriculum == null) {
            throw new StudentHasNoCurriculumException();
        }

        if (offer.isEmpty()) throw new IdDoesNotExistException();
        if (offerApplicationRepository.existsByOfferAndCurriculum(offer.get(), curriculum))
            throw new StudentAlreadyAppliedToOfferException();

        OfferApplication offerApplication = new OfferApplication();
        offerApplication.setOffer(offer.get());
        offerApplication.setCurriculum(student.getPrincipalCurriculum());
        offerApplication.setStatus(Status.CV_ENVOYE);

        return offerApplicationRepository.save(offerApplication);
    }

    public List<OfferApplication> getAllByOfferCreatorEmail(String email) throws EmailDoesNotExistException {
        Assert.isTrue(email != null, "Le courriel ne peut pas être null");
        if (isEmailInvalid(email))
            throw new EmailDoesNotExistException();
        return offerApplicationRepository.getAllByOffer_CreatorEmail(email);
    }

    private boolean isEmailInvalid(String email) {
        return !offerApplicationRepository.existsByOffer_CreatorEmail(email);
    }
}
