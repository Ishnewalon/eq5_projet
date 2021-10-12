package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.StudentAlreadyExistsException;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.StudentRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements ICrudService<Student, Long> {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> create(Student student) throws StudentAlreadyExistsException {
        Assert.isTrue(student != null, "Student is null");
        if (isNotValid(student)) {
            throw new StudentAlreadyExistsException();
        }
        return Optional.of(studentRepository.save(student));
    }

    @Override
    public Optional<Student> getOneByID(Long aLong) {
        if (aLong != null && studentRepository.existsById(aLong)) {
            return Optional.of(studentRepository.getById(aLong));
        }
        return Optional.empty();
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> update(Student student, Long aLong) throws ValidationException {
        if (aLong != null && studentRepository.existsById(aLong) && student != null) {
            student.setId(aLong);
            return Optional.of(studentRepository.save(student));
        }
        return Optional.empty();
    }

    public Optional<Student> getOneByEmailAndPassword(String email, String password) {
        if (studentRepository.existsByEmailAndPassword(email, password)) {
            return Optional.of(studentRepository.findStudentByEmailAndPassword(email, password));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteByID(Long aLong) {
        if (aLong != null && studentRepository.existsById(aLong)) {
            studentRepository.deleteById(aLong);
            return true;
        }
        return false;
    }

    private boolean isNotValid(Student student) {
        return student.getEmail() != null && studentRepository.existsByEmail(student.getEmail());
    }
}
