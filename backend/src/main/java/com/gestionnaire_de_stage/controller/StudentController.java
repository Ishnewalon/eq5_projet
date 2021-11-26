package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.dto.StudentMonitorOfferDTO;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyExistsException;
import com.gestionnaire_de_stage.model.Stage;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.service.ContractService;
import com.gestionnaire_de_stage.service.StageService;
import com.gestionnaire_de_stage.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StageService stageService;
    private final ContractService contractService;

    public StudentController(StudentService studentService,
                             StageService stageService,
                             ContractService contractService) {
        this.studentService = studentService;
        this.stageService = stageService;
        this.contractService = contractService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Student student) {
        Student createdStudent;
        try {
            createdStudent = studentService.create(student);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @GetMapping("/matricule/{matricule}")
    public ResponseEntity<?> checkValidMatricule(@PathVariable String matricule) {
        return ResponseEntity.ok(!studentService.isMatriculeValid(matricule));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> checkValidEmail(@PathVariable String email) {
        return ResponseEntity.ok(studentService.isEmailInvalid(email));
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        Student student;
        try {
            student = studentService.getOneByEmailAndPassword(email, password);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/set_principal/{studentID}/{curriculumID}")
    public ResponseEntity<?> setPrincipalCurriculum(@PathVariable long studentID, @PathVariable long curriculumID) {
        try {
            Student student = studentService.getOneByID(studentID);
            studentService.setPrincipalCurriculum(student, curriculumID);
            return ResponseEntity.ok(new ResponseMessage("CV principal changé"));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }

    @GetMapping("/no_cv")
    public List<Student> getAllStudentsWithoutCv() {
        return studentService.getAllStudentWithoutCv();
    }

    @GetMapping("/cv_invalid")
    public List<Student> getAllStudentsWithInvalidCv() {
        return studentService.getAllStudentWithInvalidCv();
    }

    @GetMapping("/needAssignement")
    public ResponseEntity<?> getAllStudentsNotAssigned() {
        List<Student> studentList = studentService.getAllUnassignedStudents();
        return ResponseEntity.ok(studentList);
    }

    @GetMapping("/not_evaluated")
    public ResponseEntity<?> getAllStudentsNotYetEvaluatedAsStudentMonitorOfferDTO() {
        try {
            List<Stage> stageList = stageService.getAllWithNoEvalStagiaire();

            List<StudentMonitorOfferDTO> studentMonitorOfferDTOList =
                    contractService.stageListToStudentMonitorOfferDtoList(stageList);
            return ResponseEntity
                    .ok(studentMonitorOfferDTOList);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("/company_not_evaluated")
    public ResponseEntity<?> getAllStudentsWithCompanyNotYetEvaluatedAsStudentMonitorOfferDTO() {
        try {
            List<Stage> stageList = stageService.getAllWithNoEvalMilieu();

            List<StudentMonitorOfferDTO> studentMonitorOfferDTOList =
                    contractService.stageListToStudentMonitorOfferDtoList(stageList);
            return ResponseEntity
                    .ok(studentMonitorOfferDTOList);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
    }
}
