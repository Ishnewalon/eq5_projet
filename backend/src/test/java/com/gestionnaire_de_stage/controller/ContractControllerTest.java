package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gestionnaire_de_stage.dto.ContractStarterDto;
import com.gestionnaire_de_stage.enums.TypeSession;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyHaveAContractException;
import com.gestionnaire_de_stage.exception.StudentIsNotAssignedException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.service.ContractService;
import com.gestionnaire_de_stage.service.StageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ContractController.class)
public class ContractControllerTest {

    private final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContractService contractService;
    @MockBean
    private StageService stageService;

    @Test
    public void testManagerStartContract() throws Exception {
        when(contractService.gsStartContract(any(), any())).thenReturn(getDummyContract());
        when(contractService.updateContract(any())).thenReturn(getDummyContract());
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/contracts/start")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ContractStarterDto(1L, 1L))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Création du contrat faite avec succès");
    }

    @Test
    public void testManagerStartContract_whenContractStarterIdManagerNull() throws Exception {
        when(contractService.gsStartContract(any(), any())).thenThrow(new IllegalArgumentException("L'identifiant du gestionnaire ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/contracts/start")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ContractStarterDto(1L, null))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identifiant du gestionnaire ne peut pas être vide");
    }

    @Test
    public void testManagerStartContract_whenIdOfferApplicationNull() throws Exception {
        when(contractService.gsStartContract(any(), any())).thenThrow(new IllegalArgumentException("L'identifiant de l'application ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/contracts/start")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ContractStarterDto(null, 1L))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identifiant de l'application ne peut pas être vide");
    }

    @Test
    public void testManagerStartContract_whenStudentAlreadyHaveAContract() throws Exception {
        when(contractService.gsStartContract(any(), any())).thenThrow(new StudentAlreadyHaveAContractException("Un contrat existe déjà pour l'étudiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/contracts/start")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ContractStarterDto(null, 1L))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Un contrat existe déjà pour l'étudiant");
    }

    @Test
    public void testManagerStartContract_whenIdOfferApplicationInvalid() throws Exception {
        when(contractService.gsStartContract(any(), any())).thenThrow(new IdDoesNotExistException("Il n'y a pas d'offre associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/contracts/start")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ContractStarterDto(1L, 1L))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'offre associé à cet identifiant");
    }

    @Test
    public void testManagerStartContract_whenStudentIsntAssigned() throws Exception {
        when(contractService.gsStartContract(any(), any())).thenThrow(new StudentIsNotAssignedException("L'étudiant doit être affecté à un superviseur avant de créer un contrat"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/contracts/start")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ContractStarterDto(1L, 1L))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'étudiant doit être affecté à un superviseur avant de créer un contrat");
    }

    @Test
    public void testGetContractReadySign_withValidEntries() throws Exception {
        List<Contract> dummyContractList = getDummyContractList();
        when(contractService.getAllUnsignedContracts()).thenReturn(dummyContractList);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/contracts/ready_to_sign")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Contract> actualContractList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualContractList.size()).isEqualTo(dummyContractList.size());
    }

    @Test
    public void testSignContractPDFManager_withValidEntries() throws Exception {
        String managerSignature = "Joe Janson";
        Contract dummyContract = getDummyContract();
        String uri = "/contracts/managerSign/" + managerSignature
                + "/" + dummyContract.getId();
        when(contractService.addManagerSignature(any(), any()))
                .thenReturn(dummyContract);
        when(contractService.fillPDF(any(), any())).thenReturn(dummyContract);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put(uri)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Contrat signé");
    }

    @Test
    public void testSignContractPDFManager_withNullEntries() throws Exception {
        String managerSignature = "Joe Janson";
        Contract dummyContract = getDummyContract();
        String uri = "/contracts/managerSign/" + managerSignature
                + "/" + dummyContract.getId();
        when(contractService.addManagerSignature(any(), any()))
                .thenThrow(new IllegalArgumentException("La signature, l'id du contrat et du gestionnaire ne peuvent être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put(uri)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("La signature, l'id du contrat et du gestionnaire ne peuvent être vide");
    }

    @Test
    public void testSignContractPDFManager_withInvalidEntries() throws Exception {
        String managerSignature = "Joe Janson";
        Contract dummyContract = getDummyContract();
        String uri = "/contracts/managerSign/" + managerSignature
                + "/" + dummyContract.getId();
        when(contractService.addManagerSignature(any(), any()))
                .thenThrow(new IdDoesNotExistException("Il n'y a pas de contrat associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put(uri)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas de contrat associé à cet identifiant");
    }

    @Test
    public void testGetContractReadySignMonitor_withValidEntries() throws Exception {
        List<Contract> dummyContractList = getDummyContractList();
        Monitor dummyMonitor = getDummyMonitor();
        when(contractService.getAllUnsignedContractForMonitor(any())).thenReturn(dummyContractList);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/contracts/monitor/" + dummyMonitor.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Contract> actualContractList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualContractList.size()).isEqualTo(dummyContractList.size());
    }

    @Test
    public void testGetContractReadySignMonitor_withNullMonitorId() throws Exception {
        Monitor dummyMonitor = getDummyMonitor();
        when(contractService.getAllUnsignedContractForMonitor(any()))
                .thenThrow(new IllegalArgumentException("L'identfiant du moniteur ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/contracts/monitor/" + dummyMonitor.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identfiant du moniteur ne peut pas être vide");
    }

    @Test
    public void testGetContractReadySignMonitor_withInvalidMonitorId() throws Exception {
        Monitor dummyMonitor = getDummyMonitor();
        when(contractService.getAllUnsignedContractForMonitor(any()))
                .thenThrow(new IdDoesNotExistException("Il n'y a pas de moniteur associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/contracts/monitor/" + dummyMonitor.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas de moniteur associé à cet identifiant");
    }

    @Test
    public void testSignContractPDFMonitor_withValidEntries() throws Exception {
        String monitorSignature = "Jon Tralala";
        Contract dummyContract = getDummyContract();
        String uri = "/contracts/monitorSign/" + monitorSignature
                + "/" + dummyContract.getId();
        when(contractService.addMonitorSignature(any(), any()))
                .thenReturn(dummyContract);
        when(contractService.fillPDF(any(), any())).thenReturn(dummyContract);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put(uri)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Contrat signé");
    }

    @Test
    public void testSignContractPDFMonitor_withNullEntries() throws Exception {
        String monitorSignature = "Jon Tralala";
        Contract dummyContract = getDummyContract();
        String uri = "/contracts/monitorSign/" + monitorSignature
                + "/" + dummyContract.getId();
        when(contractService.addMonitorSignature(any(), any()))
                .thenThrow(new IllegalArgumentException("La signature et l'identifiant du contrat ne peuvent être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put(uri)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("La signature et l'identifiant du contrat ne peuvent être vide");
    }

    @Test
    public void testSignContractPDFMonitor_withInvalidEntries() throws Exception {
        String monitorSignature = "Jon Tralala";
        Contract dummyContract = getDummyContract();
        String uri = "/contracts/monitorSign/" + monitorSignature
                + "/" + dummyContract.getId();
        when(contractService.addMonitorSignature(any(), any()))
                .thenThrow(new IdDoesNotExistException("Il n'y a pas de contrat associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put(uri)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas de contrat associé à cet identifiant");
    }

    @Test
    public void testContractNeedsStudentSignature_withValidEntries() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        Contract dummyContract = getDummyContract();
        long student_id = 1L;
        when(contractService.getContractByStudentId(any())).thenReturn(dummyContract);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/contracts/student/" + student_id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Contract actualContract = MAPPER.readValue(response.getContentAsString(), Contract.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualContract.getStudent().getFirstName()).isEqualTo(dummyContract.getStudent().getFirstName());
    }

    @Test
    public void testContractNeedsStudentSignature_withNullStudentId() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        long student_id = 1L;
        when(contractService.getContractByStudentId(any())).thenThrow(new IllegalArgumentException("La signature et l'identifiant du contrat ne peuvent être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/contracts/student/" + student_id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("La signature et l'identifiant du contrat ne peuvent être vide");
    }

    @Test
    public void testContractNeedsStudentSignature_withInvalidStudentId() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        long student_id = 1L;
        when(contractService.getContractByStudentId(any())).thenThrow(new IdDoesNotExistException("Il n'y a pas d'étudiant associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/contracts/student/" + student_id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'étudiant associé à cet identifiant");
    }

    @Test
    public void testSignContractPDFStudent_withValidEntries() throws Exception {
        String studentSignature = "Tired Human";
        Contract dummyContract = getDummyContract();
        String uri = "/contracts/studentSign/" + studentSignature
                + "/" + dummyContract.getId();
        when(contractService.addStudentSignature(any(), any()))
                .thenReturn(dummyContract);
        when(contractService.fillPDF(any(), any())).thenReturn(dummyContract);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put(uri)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Contrat signé");
    }

    @Test
    public void testSignContractPDFStudent_withNullEntries() throws Exception {
        String studentSignature = "Tired Human";
        Contract dummyContract = getDummyContract();
        String uri = "/contracts/studentSign/" + studentSignature
                + "/" + dummyContract.getId();
        when(contractService.addStudentSignature(any(), any()))
                .thenThrow(new IllegalArgumentException("La signature et l'identifiant du contrat ne peuvent être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put(uri)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("La signature et l'identifiant du contrat ne peuvent être vide");
    }

    @Test
    public void testSignContractPDFStudent_withInvalidEntries() throws Exception {
        String studentSignature = "Tired Human";
        Contract dummyContract = getDummyContract();
        String uri = "/contracts/studentSign/" + studentSignature
                + "/" + dummyContract.getId();
        when(contractService.addStudentSignature(any(), any()))
                .thenThrow(new IdDoesNotExistException("Il n'y a pas de contrat associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put(uri)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas de contrat associé à cet identifiant");
    }

    @Test
    public void testGetAllSignedContractsByManager_withExistentId() throws Exception {
        List<Contract> dummyContracts = getDummyContractList();
        when(contractService.getAllSignedContractsByManager(any())).thenReturn(dummyContracts);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/contracts/manager/signed/" + 100L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Contract> returnedContracts = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedContracts)
                .isNotEmpty()
                .isEqualTo(dummyContracts);
    }

    @Test
    public void testGetAllSignedContractsByManager_withNonExistentId() throws Exception {
        long nonExistentId = 1000L;
        when(contractService.getAllSignedContractsByManager(any())).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/contracts/manager/signed/" + nonExistentId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Contract> returnedContracts = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedContracts).isEmpty();
    }

    @Test
    public void testGetAllSignedContractsByMonitor_withExistentId() throws Exception {
        List<Contract> dummyContracts = getDummyContractList();
        when(contractService.getAllSignedContractsByMonitor(any())).thenReturn(dummyContracts);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/contracts/monitor/signed/" + 100L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Contract> returnedContracts = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedContracts)
                .isNotEmpty()
                .isEqualTo(dummyContracts);
    }

    @Test
    public void testGetAllSignedContractsByMonitor_withNonExistentId() throws Exception {
        long nonExistentId = 1000L;
        when(contractService.getAllSignedContractsByManager(any())).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/contracts/monitor/signed/" + nonExistentId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Contract> returnedContracts = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedContracts).isEmpty();
    }

    @Test
    public void testGetSignedContractByStudent_withExistentId() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        Contract dummyContract = getDummyContract();
        when(contractService.getSignedContractByStudentId(any())).thenReturn(dummyContract);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/contracts/student/signed/" + 100L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Contract returnedContract = MAPPER.readValue(response.getContentAsString(), Contract.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedContract)
                .isNotNull()
                .isEqualTo(dummyContract);
    }

    @Test
    public void testGetSignedContractByStudent_withNonExistentId() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
        when(contractService.getSignedContractByStudentId(any())).thenThrow(new IdDoesNotExistException("Il n'y a pas d'étudiant associé à cet identifiant"));

        long nonExistentId = 1000L;
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/contracts/student/signed/" + nonExistentId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'étudiant associé à cet identifiant");
    }

    private Contract getDummyContract() {
        Contract dummyContract = new Contract();
        dummyContract.setId(1L);
        dummyContract.setStudent(getDummyStudent());
        dummyContract.setOffer(getDummyOffer());
        dummyContract.setManager(getDummyManager());
        dummyContract.setManagerSignature("Joe Janson");
        dummyContract.setManagerSignDate(LocalDate.now());

        Session dummySession = new Session();
        dummySession.setTypeSession(TypeSession.HIVER);
        dummySession.setYear(Year.now());
        dummyContract.setSession(dummySession);
        return dummyContract;
    }

    private Student getDummyStudent() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        dummyStudent.setLastName("Candle");
        dummyStudent.setFirstName("Tea");
        dummyStudent.setEmail("cant@outlook.com");
        dummyStudent.setPassword("cantPass");
        dummyStudent.setDepartment("info");
        dummyStudent.setMatricule("4673943");
        dummyStudent.setSupervisor(getDummySupervisor());
        return dummyStudent;
    }

    private Supervisor getDummySupervisor() {
        Supervisor dummySupervisor = new Supervisor();
        dummySupervisor.setId(1L);
        dummySupervisor.setLastName("Keys");
        dummySupervisor.setFirstName("Harold");
        dummySupervisor.setEmail("keyh@gmail.com");
        dummySupervisor.setPassword("galaxy29");
        dummySupervisor.setDepartment("Comptabilité");
        dummySupervisor.setMatricule("04736");
        return dummySupervisor;
    }

    private List<Contract> getDummyContractList() {
        List<Contract> dummyContractList = new ArrayList<>();
        Contract contract1 = new Contract();
        contract1.setId(1L);
        dummyContractList.add(contract1);
        Contract contract2 = new Contract();
        contract2.setId(2L);
        dummyContractList.add(contract2);
        Contract contract3 = new Contract();
        contract3.setId(3L);
        dummyContractList.add(contract3);

        return dummyContractList;
    }

    private Offer getDummyOffer() {
        Offer dummyOffer = new Offer();
        dummyOffer.setDepartment("Un departement");
        dummyOffer.setAddress("ajsaodas");
        dummyOffer.setId(1L);
        dummyOffer.setDescription("oeinoiendw");
        dummyOffer.setSalary(10);
        dummyOffer.setTitle("oeinoiendw");
        dummyOffer.setCreator(getDummyMonitor());
        return dummyOffer;
    }

    private Monitor getDummyMonitor() {
        Monitor dummyMonitor = new Monitor();
        dummyMonitor.setId(1L);
        dummyMonitor.setLastName("toto");
        dummyMonitor.setFirstName("titi");
        dummyMonitor.setEmail("toto@gmail.com");
        dummyMonitor.setPassword("testPassword");
        return dummyMonitor;
    }

    private Manager getDummyManager() {
        Manager dummyManager = new Manager();
        dummyManager.setPassword("Test1234");
        dummyManager.setEmail("oussamakably@gmail.com");
        dummyManager.setFirstName("Oussama");
        dummyManager.setLastName("Kably");
        dummyManager.setPhone("5143643320");
        return dummyManager;
    }
}
