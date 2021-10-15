package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.service.CurriculumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin()
@RequestMapping("/curriculum")
public class CurriculumController {

    private final CurriculumService curriculumService;

    public CurriculumController(CurriculumService curriculumService){
        this.curriculumService = curriculumService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadCurriculum(@RequestParam("file") MultipartFile file, @RequestParam("id") Long studentId){
        Optional<Curriculum> curriculum;
        try {
            curriculum = curriculumService.convertMultipartFileToCurriculum(file, studentId);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(new ResponseMessage("IO ERROR: Check file integrity!"));
        }

        if (curriculum.isPresent()) {
            curriculumService.createCurriculum(curriculum.get());
            return ResponseEntity.ok(new ResponseMessage("Success: file created successfully!"));
        }else{
            return ResponseEntity.badRequest().body(new ResponseMessage("Student Not Found with id: "+studentId));
        }
    }
}
