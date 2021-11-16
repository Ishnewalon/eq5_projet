package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.StageAlreadyExistsException;
import com.gestionnaire_de_stage.exception.StageDoesNotExistException;
import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.model.Stage;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.StageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StageServiceTest {

    @InjectMocks
    StageService stageService;

    @Mock
    private StageRepository stageRepository;

    @Test
    public void testCreate_withValidEntries() throws Exception {
        Stage dummyStage = getDummyStage();
        when(stageRepository.existsByContract_StudentMatricule(any())).thenReturn(true);
        when(stageRepository.save(any())).thenReturn(dummyStage);

        Stage actualStage = stageService.create(dummyStage, getDummyStudent().getMatricule());

        assertThat(actualStage).isEqualTo(dummyStage);
        assertThat(actualStage.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreate_withNullStage() {
        assertThrows(IllegalArgumentException.class,
                () -> stageService.create(null, getDummyStudent().getMatricule()));
    }

    @Test
    public void testCreate_withInvalidStage() {
        when(stageRepository.existsByContract_StudentMatricule(any())).thenReturn(false);

        assertThrows(StageAlreadyExistsException.class,
                () -> stageService.create(getDummyStage(), getDummyStudent().getMatricule()));
    }

    @Test
    public void testAddEvalMilieuStage_withValidEntries() throws Exception {
        Stage dummyStage = getDummyStage();
        when(stageRepository.existsById(any())).thenReturn(true);
        when(stageRepository.save(any())).thenReturn(dummyStage);

        Stage actualStage = stageService.addEvalMilieuStage(dummyStage, new ByteArrayOutputStream());

        assertThat(actualStage.getId()).isGreaterThan(0);
    }

    @Test
    public void testAddEvalMilieuStage_withNullStage() {
        assertThrows(IllegalArgumentException.class,
                () -> stageService.addEvalMilieuStage(null, new ByteArrayOutputStream()));
    }

    @Test
    public void testAddEvalMilieuStage_withInvalidEntries() {
        when(stageRepository.existsById(any())).thenReturn(false);

        assertThrows(StageDoesNotExistException.class,
                () -> stageService.addEvalMilieuStage(getDummyStage(), new ByteArrayOutputStream()));
    }

    @Test
    public void testGetStageByStudentEmail_withValidEntries() throws Exception {
        Stage dummyStage = getDummyStage();
        String dummyEmail = "like@email.com";
        when(stageRepository.existsByContract_StudentEmail(any())).thenReturn(true);
        when(stageRepository.getByContract_StudentEmail(any())).thenReturn(dummyStage);

        Stage actualStage = stageService.getStageByStudentEmail(dummyEmail);

        assertThat(actualStage.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetStageByStudentEmail_withNullEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> stageService.getStageByStudentEmail(null));
    }

    @Test
    public void testGetStageByStudentEmail_withInvalidEmail() {
        String dummyEmail = "like@email.com";
        when(stageRepository.existsByContract_StudentEmail(any())).thenReturn(false);

        assertThrows(StageDoesNotExistException.class,
                () -> stageService.getStageByStudentEmail(dummyEmail));
    }

    @Test
    public void testAddEvalStagiaire_withValidEntries() throws Exception {
        Stage dummyStage = getDummyStage();
        when(stageRepository.existsById(any())).thenReturn(true);
        when(stageRepository.save(any())).thenReturn(dummyStage);

        Stage actualStage = stageService.addEvalStagiaire(dummyStage, new ByteArrayOutputStream());

        assertThat(actualStage.getId()).isGreaterThan(0);
    }

    @Test
    public void testAddEvalStagiaire_withNullStage() {
        assertThrows(IllegalArgumentException.class,
                () -> stageService.addEvalStagiaire(null, new ByteArrayOutputStream()));
    }

    @Test
    public void testAddEvalStagiaire_withInvalidStage() {
        Stage dummyStage = getDummyStage();
        when(stageRepository.existsById(any())).thenReturn(false);

        assertThrows(StageDoesNotExistException.class,
                () -> stageService.addEvalStagiaire(dummyStage, new ByteArrayOutputStream()));
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
}
