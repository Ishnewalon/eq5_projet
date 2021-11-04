package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.service.ContractService;
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
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/contracts")
public class ContractController {

    private final ServletContext servletContext;

    private final ContractService contractService;

    private final TemplateEngine templateEngine;

    public ContractController(ContractService contractService, TemplateEngine templateEngine, ServletContext servletContext) {
        this.servletContext = servletContext;
        this.contractService = contractService;
        this.templateEngine = templateEngine;
    }

    @GetMapping("/ready_to_sign")
    public ResponseEntity<?> contractsNeedSignature() {
        List<Contract> contractList = contractService.getAllUnsignedContracts();
        return ResponseEntity.ok(contractList);
    }

    @PutMapping("/managerSign/{managerSignature}/{manager_id}/{contract_id}")
    public ResponseEntity<?> managerSignContract(HttpServletRequest request, HttpServletResponse response, @PathVariable String managerSignature, @PathVariable Long manager_id, @PathVariable Long contract_id){
        try {
            Contract contract = contractService.addManagerSignature(managerSignature, contract_id, manager_id);
            WebContext context = new WebContext(request, response, servletContext);
            context.setVariable("contract", contract);
            String contractHtml = templateEngine.process("contractTemplate", context);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(contractHtml, baos);
            contractService.fillPDF(contract, baos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(e.getMessage()));
        } catch (IdDoesNotExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Le id du contrat n'existe pas"));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Signature fait"));
    }
}
