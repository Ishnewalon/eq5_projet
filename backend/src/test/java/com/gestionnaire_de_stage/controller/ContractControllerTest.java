package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.service.ContractService;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ContractController.class)
public class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testGetContractReadySign_withValidEntries() throws Exception {
        List<Contract> dummyContractList = getDummyContractList();
        when(contractService.getAllUnsignedContracts()).thenReturn(dummyContractList);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/contracts/ready_to_sign")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Contract> actualContractList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualContractList.size()).isEqualTo(dummyContractList.size());
    }

    @Test
    public void testSignContractPDFManager_withValidEntries() throws Exception {
        String managerSignature = "Joe Janson";
        Contract dummyContract = getDummyContract();
        long manager_id = 1L;
        String uri = "/contracts/managerSign/" + managerSignature
                + "/" + dummyContract.getId() + "/" + manager_id;
        when(contractService.addManagerSignature(any(),any(),any()))
                .thenReturn(dummyContract);
        when(contractService.fillPDF(any(), any())).thenReturn(dummyContract);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Signature fait");
    }

    @Test
    public void testSignContractPDFManager_withNullEntries() throws Exception {
        String managerSignature = "Joe Janson";
        Contract dummyContract = getDummyContract();
        long manager_id = 1L;
        String uri = "/contracts/managerSign/" + managerSignature
                + "/" + dummyContract.getId() + "/" + manager_id;
        when(contractService.addManagerSignature(any(),any(),any()))
                .thenThrow(new IllegalArgumentException("La signature, l'id du contrat et du gestionnaire ne peuvent être null"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("La signature, l'id du contrat et du gestionnaire ne peuvent être null");
    }

    @Test
    public void testSignContractPDFManager_withInvalidEntries() throws Exception {
        String managerSignature = "Joe Janson";
        Contract dummyContract = getDummyContract();
        long manager_id = 1L;
        String uri = "/contracts/managerSign/" + managerSignature
                + "/" + dummyContract.getId() + "/" + manager_id;
        when(contractService.addManagerSignature(any(),any(),any()))
                .thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le id du contrat n'existe pas");
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
        List<Contract> actualContractList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualContractList.size()).isEqualTo(dummyContractList.size());
    }

    @Test
    public void testGetContractReadySignMonitor_withNullMonitorId() throws Exception {
        Monitor dummyMonitor = getDummyMonitor();
        when(contractService.getAllUnsignedContractForMonitor(any()))
                .thenThrow(new IllegalArgumentException("L'id du moniteur ne peut pas être null"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/contracts/monitor/" + dummyMonitor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'id du moniteur ne peut pas être null");
    }

    @Test
    public void testGetContractReadySignMonitor_withInvalidMonitorId() throws Exception {
        Monitor dummyMonitor = getDummyMonitor();
        when(contractService.getAllUnsignedContractForMonitor(any()))
                .thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/contracts/monitor/" + dummyMonitor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le id du moniteur n'existe pas");
    }

    @Test
    public void testSignContractPDFMonitor_withValidEntries() throws Exception {
        String monitorSignature = "Jon Tralala";
        Contract dummyContract = getDummyContract();
        String uri = "/contracts/monitorSign/" + monitorSignature
                + "/" + dummyContract.getId();
        when(contractService.addMonitorSignature(any(),any()))
                .thenReturn(dummyContract);
        when(contractService.fillPDF(any(), any())).thenReturn(dummyContract);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Signature fait");
    }

    @Test
    public void testSignContractPDFMonitor_withNullEntries() throws Exception {
        String monitorSignature = "Jon Tralala";
        Contract dummyContract = getDummyContract();
        String uri = "/contracts/monitorSign/" + monitorSignature
                + "/" + dummyContract.getId();
        when(contractService.addMonitorSignature(any(),any()))
                .thenThrow(new IllegalArgumentException("La signature et l'id du contrat ne peuvent être null"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("La signature et l'id du contrat ne peuvent être null");
    }

    @Test
    public void testSignContractPDFMonitor_withInvalidEntries() throws Exception {
        String monitorSignature = "Jon Tralala";
        Contract dummyContract = getDummyContract();
        String uri = "/contracts/monitorSign/" + monitorSignature
                + "/" + dummyContract.getId();
        when(contractService.addMonitorSignature(any(),any()))
                .thenThrow(IdDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le id du contrat n'existe pas");
    }

    private Contract getDummyContract() {
        Contract dummyContract = new Contract();
        dummyContract.setId(1L);
        dummyContract.setStudent(new Student());
        dummyContract.setOffer(getDummyOffer());
        dummyContract.setManager(getDummyManager());
        dummyContract.setManagerSignature("Joe Janson");
        dummyContract.setManagerSignDate(LocalDate.now());
        return dummyContract;
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
