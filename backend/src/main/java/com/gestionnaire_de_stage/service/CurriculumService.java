package com.gestionnaire_de_stage.service;

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
        Assert.isTrue(file != null, "File is null");
        Assert.isTrue(studentId != null, "StudentId is null");

        Student student = studentService.getOneByID(studentId);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Curriculum curriculum = new Curriculum(
                fileName,
                file.getContentType(),
                file.getBytes(),
                student
        );
        return curriculum;
    }

    public Curriculum create(Curriculum curriculum) throws IllegalArgumentException {
        Assert.isTrue(curriculum != null, "Curriculum is null");
        return curriculumRepository.save(curriculum);
    }

    public Curriculum getOneByID(Long aLong) throws IdDoesNotExistException {
        Assert.isTrue(aLong != null, "ID est null");
        if (!curriculumRepository.existsById(aLong)){
            throw new IdDoesNotExistException();
        }
        return curriculumRepository.getById(aLong);
    }

    public List<Curriculum> getAll() {
        return curriculumRepository.findAll();
    }
}
