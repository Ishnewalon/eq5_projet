package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.ContractRepository;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import java.util.List;


@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<Contract> getAllUnsignedContracts() {
        return contractRepository.getAllByManagerSignatureNull();
    }

    public Contract managerSignContract(String matricule) throws Exception{
    //public Contract managerSignContract(OfferApplication offerApplication) throws Exception{
    //    Student student = getStudentFromCurriculum(offerApplication.getCurriculum());
   //     Contract contract = contractRepository.getContractByStudent_Matricule(student.getMatricule());
        Contract contract = contractRepository.getContractByStudent_Matricule(matricule);
      //  contract.setManagerSignDate(LocalDate.now());
       // contract.setContractPDF(createContractPDF(offerApplication));
        contract.setContractPDF(createContractPDF());
        return contractRepository.save(contract);
    }

    private Student getStudentFromCurriculum(Curriculum curriculum) {
        Student student = curriculum.getStudent();
        return student;
    }

    //private byte[] createContractPDF(OfferApplication offerApplication) throws Exception {
    private byte[] createContractPDF() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(new FileInputStream("src/main/resources/test.html"), baos);

        return baos.toByteArray();
    }
}
