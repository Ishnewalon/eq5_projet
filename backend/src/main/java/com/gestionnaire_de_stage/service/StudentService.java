package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyExistsException;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.StudentRepository;
import org.springframework.util.Assert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) throws StudentAlreadyExistsException {
        Assert.isTrue(student != null, "Etudiant est null");
        if (isNotValid(student)) {
            throw new StudentAlreadyExistsException();
        }
        return studentRepository.save(student);
    }

    public Student getOneByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        if (!isIDValid(aLong)) {
            throw new IdDoesNotExistException();
        }
        return studentRepository.getById(aLong);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student update(Student student, Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        Assert.isTrue(student != null, "L'étudiant est null");
        if (!isIDValid(aLong)) {
            throw new IdDoesNotExistException();
        }
        student.setId(aLong);
        return studentRepository.save(student);
    }

    public void deleteByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        if (!isIDValid(aLong)) {
            throw new IdDoesNotExistException();
        }
        studentRepository.deleteById(aLong);
    }

    public Student getOneByEmailAndPassword(String email, String password) throws EmailAndPasswordDoesNotExistException {
        Assert.isTrue(email != null, "Le courriel est null");
        Assert.isTrue(password != null, "Le mot de passe est null");
        if (!isEmailAndPasswordValid(email, password)) {
            throw new EmailAndPasswordDoesNotExistException();
        }
        return studentRepository.findStudentByEmailAndPassword(email, password);
    }

    private boolean isNotValid(Student student) {
        return student.getEmail() != null && studentRepository.existsByEmail(student.getEmail());
    }

    private boolean isIDValid(Long id) {
        return studentRepository.existsById(id);
    }

    private boolean isEmailAndPasswordValid(String email, String password) {
        return studentRepository.existsByEmailAndPassword(email, password);
    }
}
