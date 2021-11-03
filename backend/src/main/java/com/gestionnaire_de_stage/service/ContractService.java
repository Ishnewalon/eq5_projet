package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.OfferApplication;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.ContractRepository;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
        PdfReader pdfReader = new PdfReader(new File("src/main/resources/contratTemplate.pdf"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter("c:/permits/contract.pdf");
        PdfDocument pdfDocument = new PdfDocument(pdfReader, pdfWriter);
        PdfPage pdfPage = pdfDocument.getFirstPage();
        PdfDictionary dictionary = pdfPage.getPdfObject();
        PdfObject pdfObject = dictionary.get(PdfName.Contents);
        if (pdfObject instanceof PdfStream) {
            PdfStream pdfStream = (PdfStream) pdfObject;
            byte[] data = pdfStream.getBytes();
            String changeUp = new String(data);
            changeUp = changeUp.replace("[XX]", "Jan Janson");
            System.out.println(changeUp);
            pdfStream.setData(changeUp.getBytes(StandardCharsets.UTF_8));
        }

        /*for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
            PdfPage pdfPage = pdfDocument.getPage(i);
            PdfDictionary dictionary = pdfPage.getPdfObject();
            PdfObject pdfObject = dictionary.get(PdfName.Contents);
            if (pdfObject instanceof PdfStream) {
                PdfStream pdfStream = (PdfStream) pdfObject;
                byte[] data = pdfStream.getBytes();
                String changeUp = new String(data);
                changeUp = changeUp.replace("[nom_gestionnaire]", "Jan Janson");
                System.out.println(changeUp);
                pdfStream.setData(changeUp.getBytes(StandardCharsets.UTF_8));
            }
        }*/
        pdfDocument.close();

        return baos.toByteArray();
    }
}
