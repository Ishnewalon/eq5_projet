package com.gestionnaire_de_stage.service;

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

    public Optional<Manager> getOneByID(Long id) {
        if (id == null)
            return Optional.empty();
        return managerRepository.findById(id);
    }

    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    public Optional<Manager> update(Manager manager, Long id) throws ValidationException {
        if (id != null && manager != null) {
            manager.setId(id);
            return Optional.of(managerRepository.save(manager));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Manager> getOneByEmailAndPassword(String email, String password) {
        return managerRepository.findManagerByEmailAndPassword(email, password);
    }

    public boolean deleteByID(Long id) {
        if (id != null && managerRepository.existsById(id)) {
            managerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private boolean isNotValid(Manager manager) {
        return manager.getEmail() != null && managerRepository.existsByEmail(manager.getEmail());
    }

    public boolean validateCurriculum(boolean valid, long id) throws IdDoesNotExistException {
        Student s = null;
        try {
  //          s = studentService.getOneByID(id).orElse(null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (s == null)
            return false;

        s.setCurriculumValidated(valid);

        Student resStudent = studentService.update(s, id);

        return resStudent != null && resStudent.isCurriculumValidated() == valid;
    }


}