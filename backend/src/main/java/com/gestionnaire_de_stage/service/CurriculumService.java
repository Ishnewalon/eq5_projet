package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.CurriculumAlreadyTreatedException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.CurriculumRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@Service
public class CurriculumService {

    private final CurriculumRepository curriculumRepository;
    private final StudentService studentService;

    public CurriculumService(CurriculumRepository curriculumRepository, StudentService studentService) {
        this.curriculumRepository = curriculumRepository;
        this.studentService = studentService;
    }


    public Curriculum convertMultipartFileToCurriculum(MultipartFile file, Long studentId)
            throws IOException, IdDoesNotExistException, IllegalArgumentException {
        Assert.isTrue(file != null, "Fichier est null");
        Assert.isTrue(studentId != null, "StudentId est null");

        Student student = studentService.getOneByID(studentId);

        //noinspection ConstantConditions
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        return new Curriculum(
                fileName,
                file.getContentType(),
                file.getBytes(),
                student
        );
    }

    public Curriculum create(Curriculum curriculum) throws IllegalArgumentException {
        Assert.isTrue(curriculum != null, "Curriculum est null");
        return curriculumRepository.save(curriculum);
    }

    public Curriculum getCurriculum(Long id) {
        return curriculumRepository.getById(id);
    }

    public Curriculum getOneByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        if (!curriculumRepository.existsById(aLong))
            throw new IdDoesNotExistException();
        return curriculumRepository.getById(aLong);
    }

    public Stream<Curriculum> getAllCurriculumByValidity(boolean validity) {
        return curriculumRepository.findAllByIsValid(validity).stream();
        public List<Curriculum> getAll () {
            return curriculumRepository.findAll();
        }
    }

    public List<Student> findAllStudentsWithCurriculumNotValidatedYet() {
        List<Curriculum> curriculumNotValidatedYet = curriculumRepository.findAllByIsValidIsNull();

        return getStudentList(curriculumNotValidatedYet);
    }

    private List<Student> getStudentList(List<Curriculum> curriculumNotValidatedYet) {
        return curriculumNotValidatedYet.stream().map(Curriculum::getStudent).collect(Collectors.toList());
    }

    public boolean validate(Long idCurriculum, boolean valid) throws
            IdDoesNotExistException, CurriculumAlreadyTreatedException, IllegalArgumentException {
        assertTrue(idCurriculum != null, "Erreur: Le id du curriculum ne peut pas etre null");

        Optional<Curriculum> curriculumOptional = curriculumRepository.findById(idCurriculum);

        if (curriculumOptional.isEmpty())
            throw new IdDoesNotExistException();
        if (curriculumOptional.get().getIsValid() != null)
            throw new CurriculumAlreadyTreatedException();

        Curriculum curriculum = curriculumOptional.get();
        curriculum.setIsValid(valid);
        curriculumRepository.save(curriculum);
        return true;
    }

    public Curriculum findOneById(Long idCurriculum) throws IllegalArgumentException, IdDoesNotExistException {
        assertTrue(idCurriculum != null, "Erreur: L'id du curriculum ne peut pas etre null");
        Optional<Curriculum> byId = curriculumRepository.findById(idCurriculum);
        if (byId.isEmpty())
            throw new IdDoesNotExistException();

        return byId.get();
    }
}
