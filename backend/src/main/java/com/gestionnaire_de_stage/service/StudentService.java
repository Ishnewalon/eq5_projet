package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.*;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.StudentRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CurriculumService curriculumService;

    public StudentService(StudentRepository studentRepository, @Lazy CurriculumService curriculumService) {
        this.studentRepository = studentRepository;
        this.curriculumService = curriculumService;
    }

    public Student create(Student student) throws StudentAlreadyExistsException {
        Assert.isTrue(student != null, "L'étudiant ne peut pas être vide");
        if (isNotValid(student)) {
            throw new StudentAlreadyExistsException("Un compte existe déjà pour cet étudiant");
        }
        if (isMatriculeValid(student.getMatricule())) {
            throw new StudentAlreadyExistsException("Un étudiant ayant cette matricule existe déjà");
        }
        return studentRepository.save(student);
    }

    public Student getOneByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "L'identifiant de l'étudiant ne peut pas être vide");
        if (isIDNotValid(aLong)) {
            throw new IdDoesNotExistException("Il n'y a pas d'étudiant associé à cet identifiant");
        }
        return studentRepository.getById(aLong);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student update(Student student) throws IdDoesNotExistException {
        Assert.isTrue(student != null, "L'étudiant ne peut pas être vide");
        if (isIDNotValid(student.getId()))
            throw new IdDoesNotExistException("Il n'y a pas d'étudiant associé à cet identifiant");
        return studentRepository.save(student);
    }

    public void deleteByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "L'identifiant de l'étudiant ne peut pas être vide");
        if (isIDNotValid(aLong)) {
            throw new IdDoesNotExistException("Aucun étudiant trouvé pour cet identifiant");
        }
        studentRepository.deleteById(aLong);
    }

    public Student getOneByEmailAndPassword(String email, String password) throws EmailAndPasswordDoesNotExistException {
        Assert.isTrue(email != null, "Le courriel ne peut pas être vide");
        Assert.isTrue(password != null, "Le mot de passe ne peut pas être vide");
        if (!isEmailAndPasswordValid(email, password)) {
            throw new EmailAndPasswordDoesNotExistException("Courriel ou mot de passe invalide");
        }
        return studentRepository.findStudentByEmailAndPassword(email, password);
    }

    public Student setPrincipalCurriculum(Student receivedStudent, Long idCurriculum) throws IdDoesNotExistException, CurriculumNotValidException {
        Assert.isTrue(receivedStudent != null, "L'étudiant ne peut pas être vide");
        Assert.isTrue(idCurriculum != null, "Le id curriculum ne peut pas être vide");

        Student student = getOneByID(receivedStudent.getId());
        Curriculum curriculum = curriculumService.getOneByID(idCurriculum);

        if (curriculum.getIsValid() == null
                || !curriculum.getIsValid())
            throw new CurriculumNotValidException("Le curriculum doit être valide");

        student.setPrincipalCurriculum(curriculum);
        return studentRepository.save(student);
    }

    public List<Student> getAllUnassignedStudents() {
        return studentRepository.getAllByPrincipalCurriculum_IsValidAndSupervisorNull(true);
    }

    public boolean assign(Student student, Supervisor supervisor) {
        if (student.getSupervisor() != null) {
            return false;
        }
        student.setSupervisor(supervisor);
        studentRepository.save(student);
        return true;
    }

    private boolean isNotValid(Student student) {
        return student.getEmail() != null && studentRepository.existsByEmail(student.getEmail());
    }

    public boolean isIDNotValid(Long id) {
        return !studentRepository.existsById(id);
    }

    private boolean isEmailAndPasswordValid(String email, String password) {
        return studentRepository.existsByEmailAndPassword(email, password);
    }

    public Student getOneByEmail(String email) throws DoesNotExistException {
        Assert.notNull(email, "Le courriel est null");
        if (isEmailInvalid(email))
            throw new DoesNotExistException("Le courriel n'existe pas");
        return studentRepository.getByEmail(email);
    }

    public Student changePassword(Long id, String password) throws IdDoesNotExistException {
        Student student = getOneByID(id);
        student.setPassword(password);
        return studentRepository.save(student);
    }

    public boolean isEmailInvalid(String email) {
        return !studentRepository.existsByEmail(email);
    }

    public boolean isMatriculeValid(String matricule) {
        return studentRepository.existsByMatricule(matricule);
    }

}
