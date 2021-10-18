package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.dto.ValidationCurriculum;
import com.gestionnaire_de_stage.exception.CurriculumAlreadyTreatedException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.service.CurriculumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
@RequestMapping("/curriculum")
public class CurriculumController {

    private final CurriculumService curriculumService;

    public CurriculumController(CurriculumService curriculumService) {
        this.curriculumService = curriculumService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadCurriculum(@RequestParam("file") MultipartFile file,
                                                            @RequestParam("id") Long studentId) {
        Optional<Curriculum> curriculum;
        try {
            curriculum = curriculumService.convertMultipartFileToCurriculum(file, studentId);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(new ResponseMessage("IO ERROR: Check file integrity!"));
        }

        if (curriculum.isPresent()) {
            curriculumService.createCurriculum(curriculum.get());
            return ResponseEntity.ok(new ResponseMessage("Success: file created successfully!"));
        } else {
            return ResponseEntity.badRequest().body(new ResponseMessage("Student Not Found with id: " + studentId));
        }
    }

    @GetMapping("/invalid/students")
    public ResponseEntity<?> getAllStudent_withCurriculumNotValidatedYet() {
        List<Student> student = curriculumService.findAllStudentsWithCurriculumNotValidatedYet();
        return ResponseEntity.ok(student);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody ValidationCurriculum validationCurriculum) {
        try {
            curriculumService.validate(validationCurriculum.getId(), validationCurriculum.isValid());
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: curriculum non existant!"));
        } catch (CurriculumAlreadyTreatedException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Erreur: curriculum deja traite!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        String response = validationCurriculum.isValid() ? "Succes: curriculum valide!" : "Succes: curriculum rejete!";
        return ResponseEntity.ok(response);
    }
}
