package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.CurriculumNotValidException;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyExistsException;
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
        Assert.isTrue(student != null, "Étudiant est null");
        if (isNotValid(student)) {
            throw new StudentAlreadyExistsException();
        }
        return studentRepository.save(student);
    }

    public Student getOneByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        if (isIDNotValid(aLong)) {
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
        if (isIDNotValid(aLong)) {
            throw new IdDoesNotExistException();
        }
        student.setId(aLong);
        return studentRepository.save(student);
    }

    public void deleteByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        if (isIDNotValid(aLong)) {
            throw new IdDoesNotExistException("Aucun étudiant trouvé pour cet ID");
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

    public Student setPrincipalCurriculum(Student receivedStudent, Long idCurriculum) throws IdDoesNotExistException, CurriculumNotValidException {
        Assert.isTrue(receivedStudent != null, "L'étudiant est null");
        Assert.isTrue(idCurriculum != null, "Le id curriculum est null");

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

    public List<Student> getAllStudentWithoutCv() {
        return studentRepository.findAllByPrincipalCurriculumIsNull();
    }

    public List<Student> getAllStudentWithInvalidCv() {
        return studentRepository.findAllByPrincipalCurriculumIsNullOrPrincipalCurriculum_IsValid(false);
    }

    public boolean assign(Student student, Supervisor supervisor){
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

}
