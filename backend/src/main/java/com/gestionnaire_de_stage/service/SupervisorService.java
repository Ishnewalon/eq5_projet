package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.*;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;

    private final OfferApplicationService offerApplicationService;

    public SupervisorService(SupervisorRepository supervisorRepository,
                             OfferApplicationService offerApplicationService) {
        this.supervisorRepository = supervisorRepository;
        this.offerApplicationService = offerApplicationService;
    }

    public Supervisor create(Supervisor supervisor) throws SupervisorAlreadyExistsException {
        Assert.isTrue(supervisor != null, "Superviseur ne peut pas être vide");
        if (isNotValid(supervisor)) {
            throw new SupervisorAlreadyExistsException("Un compte existe déjà pour ce superviseur");
        }
        if (isMatriculeValid(supervisor.getMatricule())) {
            throw new SupervisorAlreadyExistsException("La matricule existe déjà");
        }
        return supervisorRepository.save(supervisor);
    }

    private boolean isMatriculeValid(String matricule) {
        return supervisorRepository.existsByMatricule(matricule);
    }

    public Supervisor getOneByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "L'identifiant ne peut pas être vide");
        if (isIdNotValid(aLong)) {
            throw new IdDoesNotExistException("Il n'y a pas de superviseur associé à cet identifiant");
        }
        return supervisorRepository.getById(aLong);
    }


    public List<Supervisor> getAll() {
        return supervisorRepository.findAll();
    }

    public Supervisor update(Supervisor supervisor) throws IdDoesNotExistException {
        Assert.isTrue(supervisor != null, "Le superviseur ne peut pas être vide");
        if (isIdNotValid(supervisor.getId())) 
            throw new IdDoesNotExistException("Il n'y a pas de superviseur associé à cet identifiant");
        return supervisorRepository.save(supervisor);
    }

    public void deleteByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "L'identifiant ne peut pas être vide");
        if (isIdNotValid(aLong)) {
            throw new IdDoesNotExistException("Il n'y a pas de superviseur associé à cet identifiant");
        }
        supervisorRepository.deleteById(aLong);
    }

    public Supervisor getOneByEmailAndPassword(String email, String password) throws EmailAndPasswordDoesNotExistException {
        Assert.isTrue(email != null, "Le courriel ne peut pas être vide");
        Assert.isTrue(password != null, "Le mot de passe ne peut pas être vide");
        if (!isEmailAndPasswordValid(email, password)) {
            throw new EmailAndPasswordDoesNotExistException("Courriel ou mot de passe invalid");
        }
        return supervisorRepository.findSupervisorByEmailAndPassword(email, password);
    }

    public List<OfferApplication> getStudentsStatus(Long supervisor_id) throws IdDoesNotExistException {
        Assert.isTrue(supervisor_id != null, "L'identifiant ne peut pas être vide");
        if (isIdNotValid(supervisor_id)) {
            throw new IdDoesNotExistException("Il n'y a pas de superviseur associé à cet identifiant");
        }
        return offerApplicationService.getAllBySupervisorId(supervisor_id);
    }

    public Supervisor getOneByEmail(String email) throws DoesNotExistException {
        Assert.notNull(email, "Le courriel est obligatoire");
        if (isEmailInvalid(email))
            throw new DoesNotExistException("Le courriel n'existe pas");

        return supervisorRepository.getByEmail(email);
    }

    private boolean isNotValid(Supervisor supervisor) {
        return supervisor.getEmail() != null &&
                supervisorRepository.existsByEmail(supervisor.getEmail());
    }

    public boolean isIdNotValid(Long id) {
        return !supervisorRepository.existsById(id);
    }

    private boolean isEmailAndPasswordValid(String email, String password) {
        return supervisorRepository.existsByEmailAndPassword(email, password);
    }

    private boolean isEmailInvalid(String email) {
        return !supervisorRepository.existsByEmail(email);
    }

}
