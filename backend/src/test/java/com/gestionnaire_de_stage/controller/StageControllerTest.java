package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.EvalMilieuStageDTO;
import com.gestionnaire_de_stage.dto.EvalStagiaireDTO;
import com.gestionnaire_de_stage.exception.*;
import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.model.Stage;
import com.gestionnaire_de_stage.model.Student;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(StageController.class)
public class StageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StageService stageService;

    @MockBean
    private ContractService contractService;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testFillEvalMilieuStagePDF_withValidEntries() throws Exception {
        EvalMilieuStageDTO dummyEvalMilieuStageDTO = getDummyEvalMilieuStageDTO();
        Stage dummyStage = getDummyStage();
        when(stageService.create(any(), any())).thenReturn(dummyStage);
        when(stageService.addEvalMilieuStage( any(), any())).thenReturn(dummyStage);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/stages/supervisor/fill_form")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyEvalMilieuStageDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Évaluation remplie!");
    }


    @Test
    public void testFillEvalMilieuStagePDF_withInvalidStudentMatricule() throws Exception {
        EvalMilieuStageDTO dummyEvalMilieuStageDTO = getDummyEvalMilieuStageDTO();
        when(contractService.getContractByStudentMatricule(any())).thenThrow(MatriculeDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/stages/supervisor/fill_form")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyEvalMilieuStageDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("La matricule de l'étudiant n'existe pas");
    }

    @Test
    public void testFillEvalMilieuStagePDF_withInexistantContract() throws Exception {
        EvalMilieuStageDTO dummyEvalMilieuStageDTO = getDummyEvalMilieuStageDTO();
        when(contractService.getContractByStudentMatricule(any())).thenThrow(new ContractDoesNotExistException("Il n'y a pas de contrat qui existe pour la matricule"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/stages/supervisor/fill_form")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyEvalMilieuStageDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas de contrat qui existe pour la matricule");
    }

    @Test
    public void testFillEvalMilieuStagePDF_withNullStage() throws Exception {
        EvalMilieuStageDTO dummyEvalMilieuStageDTO = getDummyEvalMilieuStageDTO();
        when(stageService.addEvalMilieuStage(any(),any())).thenThrow(new IllegalArgumentException("Le id du stage n'existe pas"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/stages/supervisor/fill_form")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyEvalMilieuStageDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le id du stage n'existe pas");
    }

    @Test
    public void testFillEvalMilieuStagePDF_withInvalidStage() throws Exception {
        EvalMilieuStageDTO dummyEvalMilieuStageDTO = getDummyEvalMilieuStageDTO();
        when(stageService.addEvalMilieuStage(any(),any())).thenThrow(StageDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/stages/supervisor/fill_form")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyEvalMilieuStageDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le stage n'existe pas");
    }

    @Test
    public void testFillEvalMilieuStagePDF_withExistingStage() throws Exception {
        EvalMilieuStageDTO dummyEvalMilieuStageDTO = getDummyEvalMilieuStageDTO();
        when(stageService.create(any(),any())).thenThrow(new EvaluationAlreadyFilledException("L'évalutation de cet étudiant a déjà été remplie"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/stages/supervisor/fill_form")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyEvalMilieuStageDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'évalutation de cet étudiant a déjà été remplie");
    }

    @Test
    public void testFillEvalStagiairePDF_withValidEntries() throws Exception {
        EvalStagiaireDTO dummyEvalStagiaireDTO = getDummyEvalStagiaireDTO();
        Stage dummyStage = getDummyStage();
        when(stageService.getStageByStudentEmail(any())).thenReturn(dummyStage);
        when(stageService.addEvalStagiaire(any(), any())).thenReturn(dummyStage);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/stages/monitor/fill_form")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyEvalStagiaireDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Évaluation remplie!");
    }

    @Test
    public void testFillEvalStagiairePDF_withNullStudentEmail() throws Exception {
        EvalStagiaireDTO dummyEvalStagiaireDTO = getDummyEvalStagiaireDTO();
        when(stageService.getStageByStudentEmail(any())).thenThrow(new IllegalArgumentException("Le courriel de l'étudiant est null"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/stages/monitor/fill_form")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyEvalStagiaireDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le courriel de l'étudiant est null");
    }

    @Test
    public void testFillEvalStagiairePDF_withInvalidStudentEmail() throws Exception {
        EvalStagiaireDTO dummyEvalStagiaireDTO = getDummyEvalStagiaireDTO();
        when(stageService.getStageByStudentEmail(any())).thenThrow(StageDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/stages/monitor/fill_form")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyEvalStagiaireDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le stage n'existe pas pour cette étudiant");
    }

    @Test
    public void testFillEvalStagiairePDF_withNullStage() throws Exception {
        EvalStagiaireDTO dummyEvalStagiaireDTO = getDummyEvalStagiaireDTO();
        Stage dummystage = getDummyStage();
        when(stageService.getStageByStudentEmail(any())).thenReturn(dummystage);
        when(stageService.addEvalStagiaire(any(), any())).thenThrow(new IllegalArgumentException("Le stage ne peut pas être null"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/stages/monitor/fill_form")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyEvalStagiaireDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le stage ne peut pas être null");
    }

    @Test
    public void testFillEvalStagiairePDF_withInvalidStage() throws Exception {
        EvalStagiaireDTO dummyEvalStagiaireDTO = getDummyEvalStagiaireDTO();
        Stage dummystage = getDummyStage();
        when(stageService.getStageByStudentEmail(any())).thenReturn(dummystage);
        when(stageService.addEvalStagiaire(any(), any())).thenThrow(StageDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/stages/monitor/fill_form")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyEvalStagiaireDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le stage n'existe pas pour cette étudiant");
    }

    @Test
    public void testFillEvalStagiairePDF_withEvaluationAlreadyFilled() throws Exception {
        EvalStagiaireDTO dummyEvalStagiaireDTO = getDummyEvalStagiaireDTO();
        when(stageService.getStageByStudentEmail(any())).thenThrow(new EvaluationAlreadyFilledException("L'évalutation de ce stagiaire a déjà été remplie"));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/stages/monitor/fill_form")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dummyEvalStagiaireDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'évalutation de ce stagiaire a déjà été remplie");
    }

    private EvalMilieuStageDTO getDummyEvalMilieuStageDTO() {
        EvalMilieuStageDTO dummyEvalMilieuStageDTO = new EvalMilieuStageDTO();
        dummyEvalMilieuStageDTO.setEntrepriseNom("La place");
        dummyEvalMilieuStageDTO.setPersonneContact("Robert California");
        dummyEvalMilieuStageDTO.setPhone("1234567890");
        dummyEvalMilieuStageDTO.setTelecopieur("faxTime");
        dummyEvalMilieuStageDTO.setAdresse("8394 NoName Street");
        dummyEvalMilieuStageDTO.setZip("J3N L5D");
        dummyEvalMilieuStageDTO.setVille("Huron");
        dummyEvalMilieuStageDTO.setNomStagiaire("Gordon HeavyArm");
        dummyEvalMilieuStageDTO.setDateStage("12-25-2022");
        dummyEvalMilieuStageDTO.setStageCourant(2);
        dummyEvalMilieuStageDTO.setMatriculeEtudiant("123463");
        dummyEvalMilieuStageDTO.setSignatureSuperviseur("Rasputin Jkral");

        return dummyEvalMilieuStageDTO;
    }

    private Stage getDummyStage() {
        Stage dummyStage = new Stage();
        dummyStage.setId(1L);
        dummyStage.setContract(getDummyContract());
        return dummyStage;
    }

    private Contract getDummyContract() {
        Contract dummyContract = new Contract();
        dummyContract.setId(1L);
        dummyContract.setStudent(getDummyStudent());
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
        return dummyStudent;
    }

    private EvalStagiaireDTO getDummyEvalStagiaireDTO() {
        EvalStagiaireDTO dummyEvalStagiaireDTO = new EvalStagiaireDTO();
        dummyEvalStagiaireDTO.setEntrepriseNom("Entreprise A");
        dummyEvalStagiaireDTO.setNomStagiaire("Tom Thorough");
        dummyEvalStagiaireDTO.setPhone("4327659465");
        dummyEvalStagiaireDTO.setDateSignature("8-11-2021");
        dummyEvalStagiaireDTO.setEmailEtudiant("myemail@email.com");

        return dummyEvalStagiaireDTO;
    }
}
