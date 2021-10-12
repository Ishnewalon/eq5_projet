package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.CurriculumRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CurriculumService {

    private CurriculumRepository curriculumRepository;
    private StudentService studentService;

    public CurriculumService(CurriculumRepository curriculumRepository, StudentService studentService) {
        this.curriculumRepository = curriculumRepository;
        this.studentService = studentService;
    }


    public Optional<Curriculum> convertMultipartFileToCurriculum(MultipartFile file, Long studentId) throws IOException {
        try {
            Optional<Student> student = studentService.getOneByID(studentId);
            if (student.isPresent()) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());

                Curriculum curriculum = new Curriculum(
                        fileName,
                        file.getContentType(),
                        file.getBytes(),
                        student.get()
                );
                return Optional.of(curriculum);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Curriculum createCurriculum(Curriculum curriculum){
        return curriculumRepository.save(curriculum);
    }

    public Curriculum getCurriculum(Long id){
        return curriculumRepository.getById(id);
    }

    public Stream<Curriculum> getAllCurriculumByValidity(boolean validity){
        return curriculumRepository.findAllByIsValid(validity).stream();
    }
}
