package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.EvalMilieuStageDTO;
import com.gestionnaire_de_stage.dto.EvalStagiaireDTO;
import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.exception.ContractDoesNotExistException;
import com.gestionnaire_de_stage.exception.EvaluationAlreadyFilledException;
import com.gestionnaire_de_stage.exception.MatriculeDoesNotExistException;
import com.gestionnaire_de_stage.exception.StageDoesNotExistException;
import com.gestionnaire_de_stage.model.Stage;
import com.gestionnaire_de_stage.service.ContractService;
import com.gestionnaire_de_stage.service.StageService;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@RestController
@RequestMapping("/stages")
@CrossOrigin
public class StageController {

    private final StageService stageService;

    private final ContractService contractService;

    private final ServletContext servletContext;

    private final TemplateEngine templateEngine;

    public StageController(StageService stageService,
                           ContractService contractService,
                           TemplateEngine templateEngine,
                           ServletContext servletContext) {
        this.stageService = stageService;
        this.contractService = contractService;
        this.templateEngine = templateEngine;
        this.servletContext = servletContext;
    }

    @PostMapping("/supervisor/fill_form")
    public ResponseEntity<?> fillEvalMilieuStagePDF(@RequestBody EvalMilieuStageDTO evalMilieuStageDTO, HttpServletRequest request, HttpServletResponse response) {
        Stage stage = new Stage();
        evalMilieuStageDTO.setSignatureDate(LocalDate.now());
        try {
            stage.setContract(contractService.getContractByStudentMatricule(evalMilieuStageDTO.getMatriculeEtudiant()));
            stage = stageService.create(stage, evalMilieuStageDTO.getMatriculeEtudiant());
            WebContext context = new WebContext(request, response, servletContext);
            context.setVariable("formInfo", evalMilieuStageDTO);
            String evalMilieuStageHtml = templateEngine.process("evalMilieuStage", context);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(evalMilieuStageHtml, baos);
            stageService.addEvalMilieuStage(stage, baos);
        } catch (MatriculeDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("La matricule de l'étudiant n'existe pas"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        } catch (StageDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Le stage n'existe pas"));
        } catch (ContractDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Cet étudiant n'a pas de stage"));
        } catch (EvaluationAlreadyFilledException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Évaluation remplie!"));
    }

    @PostMapping("/monitor/fill_form")
    public ResponseEntity<?> fillEvalStagiairePDF(@RequestBody EvalStagiaireDTO evalStagiaireDTO, HttpServletRequest request, HttpServletResponse response) {
        Stage stage;
        try {
            stage = stageService.getStageByStudentEmail(evalStagiaireDTO.getEmailEtudiant());
            WebContext context = new WebContext(request, response, servletContext);
            context.setVariable("formInfo", evalStagiaireDTO);
            String contractHtml = templateEngine.process("evalStagiaire", context);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(contractHtml, baos);

            stageService.addEvalStagiaire(stage, baos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        } catch (StageDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Le stage n'existe pas pour cette étudiant"));
        } catch (EvaluationAlreadyFilledException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Évaluation remplie!"));
    }
}
