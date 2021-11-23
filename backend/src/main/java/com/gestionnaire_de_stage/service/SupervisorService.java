package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.DoesNotExistException;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.SupervisorAlreadyExistsException;
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
        Assert.isTrue(supervisor != null, "Superviseur est null");
        if (isNotValid(supervisor)) {
            throw new SupervisorAlreadyExistsException();
        }
        return supervisorRepository.save(supervisor);
    }

    public Supervisor getOneByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        if (isIdNotValid(aLong)) {
            throw new IdDoesNotExistException();
        }
        return supervisorRepository.getById(aLong);
    }


    public List<Supervisor> getAll() {
        return supervisorRepository.findAll();
    }

    public Supervisor update(Supervisor supervisor) throws IdDoesNotExistException {
        Assert.isTrue(supervisor != null, "Le superviseur est null");
        if (isIdNotValid(supervisor.getId())) 
            throw new IdDoesNotExistException();
        return supervisorRepository.save(supervisor);
    }

    public void deleteByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        if (isIdNotValid(aLong)) {
            throw new IdDoesNotExistException();
        }
        supervisorRepository.deleteById(aLong);
    }

    public Supervisor getOneByEmailAndPassword(String email, String password) throws EmailAndPasswordDoesNotExistException {
        Assert.isTrue(email != null, "Le courriel est null");
        Assert.isTrue(password != null, "Le mot de passe est null");
        if (!isEmailAndPasswordValid(email, password)) {
            throw new EmailAndPasswordDoesNotExistException();
        }
        return supervisorRepository.findSupervisorByEmailAndPassword(email, password);
    }

    public List<OfferApplication> getStudentsStatus(Long supervisor_id) throws IdDoesNotExistException {
        Assert.isTrue(supervisor_id != null, "L'id ne peut pas être null");
        if (isIdNotValid(supervisor_id)) {
            throw new IdDoesNotExistException();
        }
        return offerApplicationService.getAllBySupervisorId(supervisor_id);
    }

    public Supervisor getOneByEmail(String email) throws DoesNotExistException {
        Assert.notNull(email, "Le email est obligatoire");
        if (isEmailInvalid(email))
            throw new DoesNotExistException("L'email n'existe pas");

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
