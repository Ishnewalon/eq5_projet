package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerService implements ICrudService<Manager, Long> {

    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository){
        this.managerRepository = managerRepository;
    }

    @Autowired
    private StudentService studentService;

    @Override
    public Optional<Manager> create(Manager manager) throws ValidationException {
        return manager != null ? Optional.of(managerRepository.save(manager)) : Optional.empty();
    }

    @Override
    public Optional<Manager> getOneByID(Long id) {
        if (id == null)
            return Optional.empty();
        return managerRepository.findById(id);
    }

    @Override
    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    @Override
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

    @Override
    public boolean deleteByID(Long id) {
        if (id != null && managerRepository.existsById(id)) {
            managerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean validateCurriculum(boolean valid, long id) {
        Student s = studentService.getOneByID(id).orElse(null);

        if (s == null)
            return false;

        s.setCurriculumValidated(valid);

        Optional<Student> resStudent = studentService.update(s, id);

        return resStudent.isPresent() && resStudent.get().isCurriculumValidated() == valid;
    }
}