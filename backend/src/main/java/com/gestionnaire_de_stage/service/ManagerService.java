package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.ManagerAlreadyExistsException;
import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final StudentService studentService;

    public ManagerService(ManagerRepository managerRepository, StudentService studentService){
        this.managerRepository = managerRepository;
        this.studentService = studentService;
    }

    public Manager create(Manager manager) throws ManagerAlreadyExistsException {
        Assert.isTrue(manager != null, "Le gestionnaire est null");
        if (isNotValid(manager)) {
            throw new ManagerAlreadyExistsException();
        }
        return managerRepository.save(manager);
    }

    public Manager getOneByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "Id est null");
        if (!isIDValid(aLong)) {
            throw new IdDoesNotExistException();
        }
        return managerRepository.getById(aLong);
    }

    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    public Manager update(Manager manager, Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        Assert.isTrue(manager != null, "Le gestionnaire est null");
        if (!isIDValid(aLong)) {
            throw new IdDoesNotExistException();
        }
        manager.setId(aLong);
        return managerRepository.save(manager);
    }

    public void deleteByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        if (!isIDValid(aLong)) {
            throw new IdDoesNotExistException();
        }
        managerRepository.deleteById(aLong);
    }

    public Manager getOneByEmailAndPassword(String email, String password) throws EmailAndPasswordDoesNotExistException {
        Assert.isTrue(email != null, "Le courriel est null");
        Assert.isTrue(password != null, "Le mot de passe est null");
        if (!isEmailAndPasswordValid(email, password)) {
            throw new EmailAndPasswordDoesNotExistException();
        }
        return managerRepository.findManagerByEmailAndPassword(email, password);
    }

    private boolean isNotValid(Manager manager) {
        return manager.getEmail() != null && managerRepository.existsByEmail(manager.getEmail());
    }

    private boolean isIDValid(Long id) {
        return managerRepository.existsById(id);
    }

    private boolean isEmailAndPasswordValid(String email, String password) {
        return managerRepository.existsByEmailAndPassword(email, password);
    }

    public boolean validateCurriculum(boolean valid, long id) throws IdDoesNotExistException {
        Student student = studentService.getOneByID(id);
        student.setCurriculumValidated(valid);

        student = studentService.update(student, id);

        return student != null && student.isCurriculumValidated() == valid;
    }


}