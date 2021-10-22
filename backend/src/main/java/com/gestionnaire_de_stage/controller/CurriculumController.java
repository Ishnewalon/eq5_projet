package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.dto.ValidationCurriculum;
import com.gestionnaire_de_stage.exception.CurriculumAlreadyTreatedException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.service.CurriculumService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/curriculum")
public class CurriculumController {

    private final CurriculumService curriculumService;

    public CurriculumController(CurriculumService curriculumService) {
        this.curriculumService = curriculumService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadCurriculum(@RequestParam("file") MultipartFile file,
                                                            @RequestParam("id") Long studentId) {
        Curriculum curriculum;
        try {
            curriculum = curriculumService.convertMultipartFileToCurriculum(file, studentId);
        } catch (IOException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("IO Error: check file integrity!"));
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Invalid Student ID"));
        }

        curriculumService.create(curriculum);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage("File Uploaded Successfully"));
    }

    @GetMapping("/invalid/students")
    public ResponseEntity<?> getAllCurriculumNotValidatedYet() {
        List<Curriculum> curriculumList = curriculumService.findAllCurriculumNotValidatedYet();
        return ResponseEntity.ok(curriculumList);
    }

    @GetMapping("/valid/students")
    public ResponseEntity<?> getAllCurriculumValidated() {
        List<Curriculum> curriculumList = curriculumService.findAllCurriculumValidated();
        return ResponseEntity.ok(curriculumList);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody ValidationCurriculum validationCurriculum) {
        try {
            curriculumService.validate(validationCurriculum.getId(), validationCurriculum.isValid());
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Curriculum non existant!"));
        } catch (CurriculumAlreadyTreatedException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Curriculum déjà traité!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        String response = validationCurriculum.isValid() ? "Curriculum validé!" : "Curriculum rejeté!";
        return ResponseEntity.ok(new ResponseMessage(response));
    }

    @GetMapping({"/download", "/download/{idCurriculum}"})
    public ResponseEntity<?> downloadById(@PathVariable(required = false) Long idCurriculum) {
        System.out.println(idCurriculum);
        Curriculum oneById;
        try {
            oneById = curriculumService.findOneById(idCurriculum);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Curriculum non existant!"));
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.attachment().filename(oneById.getName()).build().toString());

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(oneById.getData());
    }
}
