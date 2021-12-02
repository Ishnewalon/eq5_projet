package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.StudentCurriculumsDTO;
import com.gestionnaire_de_stage.exception.CurriculumAlreadyTreatedException;
import com.gestionnaire_de_stage.exception.CurriculumUsedException;
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

@Service
public class CurriculumService {

    private final CurriculumRepository curriculumRepository;
    private final StudentService studentService;
    private final OfferApplicationService offerApplicationService;

    public CurriculumService(
            CurriculumRepository curriculumRepository,
            StudentService studentService,
            OfferApplicationService offerApplicationService) {
        this.curriculumRepository = curriculumRepository;
        this.studentService = studentService;
        this.offerApplicationService = offerApplicationService;
    }

    public Curriculum convertMultipartFileToCurriculum(MultipartFile file, Long studentId)
            throws IOException, IdDoesNotExistException, IllegalArgumentException {
        Assert.isTrue(file != null, "Fichier ne peut pas être vide");
        Assert.isTrue(studentId != null, "L'identifiant de l'étudiant ne peut pas être vide");

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
        Assert.isTrue(curriculum != null, "Le curriculum ne peut pas être vide");
        return curriculumRepository.save(curriculum);
    }

    public Curriculum getOneByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "L'identifiant du curriculum ne peut pas être vide");
        if (!curriculumRepository.existsById(aLong))
            throw new IdDoesNotExistException("Il n'y a pas de curriculum associé à cet identifiant");
        return curriculumRepository.getById(aLong);
    }

    public List<Curriculum> findAllByStudent(Student student) throws IllegalArgumentException {
        Assert.notNull(student, "L'etudiant ne peut pas être vide");

        return curriculumRepository.findAllByStudent(student);
    }

    public StudentCurriculumsDTO allCurriculumsByStudentAsStudentCurriculumsDTO(Student student) throws IllegalArgumentException {
        List<Curriculum> curriculumListByStudent = findAllByStudent(student);
        Curriculum principal;

        int index = curriculumListByStudent.indexOf(student.getPrincipalCurriculum());
        principal = curriculumListByStudent.contains(student.getPrincipalCurriculum())
                ? curriculumListByStudent.get(index) : null;

        return new StudentCurriculumsDTO(principal, curriculumListByStudent);
    }

    @SuppressWarnings("SameReturnValue")
    public boolean validate(Long idCurriculum, boolean valid) throws
            IdDoesNotExistException, CurriculumAlreadyTreatedException, IllegalArgumentException {
        Assert.isTrue(idCurriculum != null, "L'identifiant du curriculum ne peut pas être vide");

        Optional<Curriculum> curriculumOptional = curriculumRepository.findById(idCurriculum);

        if (curriculumOptional.isEmpty())
            throw new IdDoesNotExistException("Il n'y a pas de curriculum associé à cet identifiant");
        if (curriculumOptional.get().getIsValid() != null)
            throw new CurriculumAlreadyTreatedException("Ce curriculum a déjà été traité");

        Curriculum curriculum = curriculumOptional.get();
        curriculum.setIsValid(valid);
        curriculumRepository.save(curriculum);
        return true;
    }

    public Curriculum findOneById(Long idCurriculum) throws IllegalArgumentException, IdDoesNotExistException {
        Assert.isTrue(idCurriculum != null, "L'identifiant du curriculum ne peut pas être vide");
        Optional<Curriculum> byId = curriculumRepository.findById(idCurriculum);
        if (byId.isEmpty())
            throw new IdDoesNotExistException("Il n'y a pas de curriculum associé à cet identifiant");

        return byId.get();
    }

    public List<Curriculum> findAllByStudentId(Long id) throws IdDoesNotExistException {
        Student student = studentService.getOneByID(id);
        return findAllByStudent(student);
    }

    public void deleteOneById(Long idCurriculum) throws IllegalArgumentException, IdDoesNotExistException, CurriculumUsedException {
        Assert.notNull(idCurriculum, "L'identifiant du curriculum ne peut pas être null");
        Curriculum curriculum = getOneByID(idCurriculum);

        if (isPrincipal(curriculum))
            throw new CurriculumUsedException("Impossible de supprimer. C'est votre curriculum par défaut");

        if (offerApplicationService.isCurriculumInUse(curriculum))
            throw new CurriculumUsedException("Impossible de supprimer. Vous avez postulé avec ce curriculum");

        curriculumRepository.deleteById(idCurriculum);
    }

    private boolean isPrincipal(Curriculum curriculum) {
        Student student = curriculum.getStudent();
        return curriculum == student.getPrincipalCurriculum();
    }
}
