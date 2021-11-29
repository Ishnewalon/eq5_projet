package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.StudentMonitorOfferDTO;
import com.gestionnaire_de_stage.exception.CurriculumNotValidException;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.StudentAlreadyExistsException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.service.ContractService;
import com.gestionnaire_de_stage.service.StageService;
import com.gestionnaire_de_stage.service.StudentService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private ContractService contractService;

    @MockBean
    private StageService stageService;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testStudentSignUp_withValidEntries() throws Exception {
        Student dummyStudent = getDummyStudent();
        when(studentService.create(any())).thenReturn(dummyStudent);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/student/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyStudent)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Student actualStudent = MAPPER.readValue(response.getContentAsString(), Student.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualStudent).isEqualTo(dummyStudent);
    }


    @Test
    public void testStudentSignUp_withNullStudent() throws Exception {
        Student dummyStudent = getDummyStudent();
        when(studentService.create(any())).thenThrow(new IllegalArgumentException("L'étudiant ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/student/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyStudent)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'étudiant ne peut pas être vide");
    }

    @Test
    public void testStudentSignUp_withInvalidStudent() throws Exception {
        Student dummyStudent = getDummyStudent();
        when(studentService.create(any())).thenThrow(new StudentAlreadyExistsException("Un compte existe déjà pour cet étudiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/student/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyStudent)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Un compte existe déjà pour cet étudiant");
    }

    @Test
    public void testStudentLogin_withValidEntries() throws Exception {
        Student dummyStudent = getDummyStudent();
        String email = "clip@gmail.com";
        String password = "thiswilldo";
        when(studentService.getOneByEmailAndPassword(any(), any())).thenReturn(dummyStudent);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Student actualStudent = MAPPER.readValue(response.getContentAsString(), Student.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualStudent.getLastName()).isEqualTo("Brawl");
    }

    @Test
    public void testStudentLogin_withNullEntries() throws Exception {
        String email = "clip@gmail.com";
        String password = "thiswilldo";
        when(studentService.getOneByEmailAndPassword(any(), any())).thenThrow(new IllegalArgumentException("Courriel et mot de passe ne peuvent pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Courriel et mot de passe ne peuvent pas être vide");
    }

    @Test
    public void testStudentLogin_withInvalidEntries() throws Exception {
        String email = "clip@gmail.com";
        String password = "thiswilldo";
        when(studentService.getOneByEmailAndPassword(any(), any())).thenThrow(new EmailAndPasswordDoesNotExistException("Courriel ou mot de passe invalide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/" + email + "/" + password)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Courriel ou mot de passe invalide");
    }

    @Test
    public void testSetPrincipalCurriculum_withValidEntries() throws Exception {
        Student student = getDummyStudent();
        Curriculum curriculum = getDummyCurriculum();
        when(studentService.getOneByID(any())).thenReturn(student);
        student.setPrincipalCurriculum(curriculum);
        when(studentService.setPrincipalCurriculum(any(), any())).thenReturn(student);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/set_principal/" + student.getId() + "/" + curriculum.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("CV principal changé");
    }

    @Test
    void testSetPrincipalCurriculum_withNullEntries() throws Exception {
        Student student = getDummyStudent();
        Curriculum curriculum = getDummyCurriculum();
        when(studentService.getOneByID(any()))
                .thenThrow(new IllegalArgumentException("L'identifiant de l'étudiant ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/set_principal/" + student.getId() + "/" + curriculum.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("L'identifiant de l'étudiant ne peut pas être vide");
    }

    @Test
    void testSetPrincipalCurriculum_withEntriesNotExists() throws Exception {
        Student student = getDummyStudent();
        Curriculum curriculum = getDummyCurriculum();
        when(studentService.getOneByID(any()))
                .thenThrow(new IdDoesNotExistException("Il n'y a pas d'étudiant associé à cet identifiant"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/set_principal/" + student.getId() + "/" + curriculum.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Il n'y a pas d'étudiant associé à cet identifiant");
    }

    @Test
    void testSetPrincipalCurriculum_CurriculumInvalid() throws Exception {
        Student student = getDummyStudent();
        Curriculum curriculum = getDummyCurriculum();
        when(studentService.getOneByID(any())).thenReturn(student);
        when(studentService.setPrincipalCurriculum(any(), any()))
                .thenThrow(new CurriculumNotValidException("Le curriculum doit être valide"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/set_principal/" + student.getId() + "/" + curriculum.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le curriculum doit être valide");
    }

    @Test
    public void testGetAllStudents() throws Exception {
        List<Student> list = Arrays.asList(new Student(), new Student());
        when(studentService.getAll()).thenReturn(Arrays.asList(new Student(), new Student()));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        var actual = mvcResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(actual,
                new TypeReference<List<Student>>() {
                })).isEqualTo(list);

    }

    @Test
    public void testGetAllStudentsNotAssigned() throws Exception {
        List<Student> list = Arrays.asList(new Student(), new Student());
        when(studentService.getAllUnassignedStudents()).thenReturn(Arrays.asList(new Student(), new Student()));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/needAssignement")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Student> actualStudentList = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualStudentList).isEqualTo(list);
    }

    @Test
    public void testGetAllStudentsNotYetEvaluatedAsStudentMonitorOfferDTO() throws Exception {
        List<StudentMonitorOfferDTO> studentMonitorOfferDTOList = getDummyStudentMonitorOfferDTOList();
        when(stageService.getAllWithNoEvalStagiaire()).thenReturn(getDummyStageList());
        when(contractService.stageListToStudentMonitorOfferDtoList(any())).thenReturn(studentMonitorOfferDTOList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/not_evaluated")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        final List<StudentMonitorOfferDTO> actual =
                MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
                });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actual.size()).isEqualTo(studentMonitorOfferDTOList.size());
    }

    @Test
    public void testGetAllStudentsNotYetEvaluatedAsStudentMonitorOfferDTOThrowsIllegalArg() throws Exception {
        when(stageService.getAllWithNoEvalStagiaire()).thenReturn(getDummyStageList());
        when(contractService.stageListToStudentMonitorOfferDtoList(any()))

                .thenThrow(new IllegalArgumentException("La liste de stage ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/not_evaluated")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("La liste de stage ne peut pas être vide");
    }

    @Test
    public void testGetAllStudentsWithCompanyNotYetEvaluatedAsStudentMonitorOfferDTO() throws Exception {
        List<StudentMonitorOfferDTO> studentMonitorOfferDTOList = getDummyStudentMonitorOfferDTOList();
        when(stageService.getAllWithNoEvalMilieu()).thenReturn(getDummyStageList());
        when(contractService.stageListToStudentMonitorOfferDtoList(any())).thenReturn(studentMonitorOfferDTOList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/company_not_evaluated")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        final List<StudentMonitorOfferDTO> actual =
                MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
                });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actual.size()).isEqualTo(studentMonitorOfferDTOList.size());
    }

    @Test
    public void testGetAllStudentsWithCompanyNotYetEvaluatedAsStudentMonitorOfferDTOThrowsIllegalArg() throws Exception {
        when(stageService.getAllWithNoEvalMilieu()).thenReturn(getDummyStageList());
        when(contractService.stageListToStudentMonitorOfferDtoList(any()))
                .thenThrow(new IllegalArgumentException("La liste de stage ne peut pas être vide"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/company_not_evaluated")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("La liste de stage ne peut pas être vide");
    }

    @Test
    public void testCheckMatricule() throws Exception {
        when(studentService.isMatriculeValid(any())).thenReturn(false);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/student/matricule/{matricule}", "1234567")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("true");
    }

    @Test
    public void testCheckEmailValidty() throws Exception {
        when(studentService.isEmailInvalid(any())).thenReturn(false);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/student/email/{email}", "potato@mail.com")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("false");
    }

    @Test
    public void testChangePassword() throws Exception {
        Student dummyStudent = getDummyStudent();
        String newPassword = "newPassword";
        when(studentService.changePassword(any(), any())).thenReturn(dummyStudent);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/change_password/{id}", dummyStudent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPassword))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Mot de passe changé avec succès");
    }


    private StudentMonitorOfferDTO getDummyStudentMonitorOfferDTO() {
        return new StudentMonitorOfferDTO(
                getDummyStudent(),
                getDummyMonitor(),
                getDummyOffer()
        );
    }

    private List<StudentMonitorOfferDTO> getDummyStudentMonitorOfferDTOList() {
        StudentMonitorOfferDTO dto1 = getDummyStudentMonitorOfferDTO();
        StudentMonitorOfferDTO dto2 = getDummyStudentMonitorOfferDTO();
        StudentMonitorOfferDTO dto3 = getDummyStudentMonitorOfferDTO();
        return new ArrayList<>(Arrays.asList(dto1, dto2, dto3));
    }

    private Offer getDummyOffer() {
        Offer dummyOffer = new Offer();
        dummyOffer.setDepartment("Un departement");
        dummyOffer.setAddress("ajsaodas");
        dummyOffer.setId(1L);
        dummyOffer.setDescription("oeinoiendw");
        dummyOffer.setSalary(10);
        dummyOffer.setTitle("oeinoiendw");
        return dummyOffer;
    }

    private Monitor getDummyMonitor() {
        Monitor dummyMonitor = new Monitor();
        dummyMonitor.setId(1L);
        dummyMonitor.setFirstName("same");
        dummyMonitor.setLastName("dude");
        dummyMonitor.setEmail("dudesame@gmail.com");
        dummyMonitor.setPhone("5145555112");
        dummyMonitor.setDepartment("Informatique");
        dummyMonitor.setPassword("testPassword");
        return dummyMonitor;
    }

    private Student getDummyStudent() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        dummyStudent.setLastName("Brawl");
        dummyStudent.setFirstName("Spaghetta");
        dummyStudent.setPhone("514-546-2375");
        dummyStudent.setEmail("clip@gmail.com");
        dummyStudent.setPassword("thiswilldo");
        dummyStudent.setAddress("758 George");
        dummyStudent.setCity("LaSalle");
        dummyStudent.setDepartment("Informatique");
        dummyStudent.setPostalCode("H5N 9F2");
        dummyStudent.setMatricule("1740934");

        return dummyStudent;
    }

    private Curriculum getDummyCurriculum() {
        Curriculum curriculum = new Curriculum();
        curriculum.setName("myFileeee");
        curriculum.setType("pdf");
        curriculum.setId(1L);
        return curriculum;
    }

    private Contract getDummyContract() {
        Contract dummyContract = new Contract();
        dummyContract.setId(1L);
        dummyContract.setStudent(getDummyStudent());
        dummyContract.setMonitor(getDummyMonitor());
        dummyContract.setOffer(getDummyOffer());
        return dummyContract;
    }

    private Stage getDummyStage() {
        Stage dummyStage = new Stage();
        dummyStage.setId(1L);
        dummyStage.setContract(getDummyContract());
        return dummyStage;
    }

    private List<Stage> getDummyStageList() {
        Stage stage1 = getDummyStage();
        Stage stage2 = getDummyStage();
        stage2.setId(2L);

        Stage stage3 = getDummyStage();
        stage3.setId(3L);
        return List.of(stage1, stage2, stage3);
    }
}
