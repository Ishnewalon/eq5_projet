package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.ResponseMessage;
import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.repository.ContractRepository;
import com.gestionnaire_de_stage.service.ContractService;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@RestController
@CrossOrigin
@RequestMapping("/pdf")
public class PDFController {

    @Autowired
    ServletContext servletContext;

    @Autowired
    ContractService contractService;

    private final TemplateEngine templateEngine;

    public PDFController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @RequestMapping(path = "/pdf/{contract_id}")
    public ResponseEntity<?> createPDF(HttpServletRequest request, HttpServletResponse response, @PathVariable Long contract_id) throws Exception{
        Contract contract = contractService.getContractById(contract_id);
        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("contract", contract);
        String contractHtml = templateEngine.process("test", context);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(contractHtml, baos);
        return ResponseEntity.ok().body(new ResponseMessage("It has been created"));
    }


}
