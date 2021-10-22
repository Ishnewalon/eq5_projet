package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.SupervisorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupervisorServiceTest {

    @InjectMocks
    SupervisorService supervisorService;

    @Mock
    SupervisorRepository supervisorRepository;

    @Test
    public void testCreate_withValidSupervisor() throws SupervisorAlreadyExistsException {
        Supervisor dummySupervisor = getDummySupervisor();
        when(supervisorRepository.save(any())).thenReturn(dummySupervisor);

        Supervisor actualSupervisor = supervisorService.create(dummySupervisor);

        assertThat(actualSupervisor.getEmail()).isEqualTo(dummySupervisor.getEmail());
    }

    @Test
    public void testCreate_withNullSupervisor() {
        assertThrows(IllegalArgumentException.class,
                () -> supervisorService.create(null));
    }

    @Test
    public void testCreate_alreadyExistsStudent() {
        when(supervisorRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(SupervisorAlreadyExistsException.class,
                () -> supervisorService.create(getDummySupervisor()));
    }

    @Test
    public void testGetByID_withValidID() throws IdDoesNotExistException {
        Long validID = 1L;
        Supervisor dummySupervisor = getDummySupervisor();
        when(supervisorRepository.existsById(any())).thenReturn(true);
        when(supervisorRepository.getById(any())).thenReturn(dummySupervisor);

        Supervisor actualSupervisor = supervisorService.getOneByID(validID);

        assertThat(actualSupervisor.getMatricule()).isEqualTo(dummySupervisor.getMatricule());
    }

    @Test
    public void testGetByID_withNullID() {
        assertThrows(IllegalArgumentException.class,
                () -> supervisorService.getOneByID(null));
    }

    @Test
    public void testGetByID_doesntExistID() {
        Supervisor dummySupervisor = getDummySupervisor();
        when(supervisorRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> supervisorService.getOneByID(dummySupervisor.getId()));
    }

    @Test
    public void testGetAll() {
        when(supervisorRepository.findAll()).thenReturn(getDummySupervisorList());

        final List<Supervisor> actualSupervisorList = supervisorService.getAll();

        assertThat(actualSupervisorList.size()).isEqualTo(3);
        assertThat(actualSupervisorList.get(0).getFirstName()).isEqualTo("Harold");
    }

    @Test
    public void testAssign(){
        Supervisor supervisor = getDummySupervisor();
        Student student = getDummyStudent();

        supervisorService.assign(student, supervisor);

        assertThat(supervisor.getStudentList().size()).isEqualTo(1);
        assertThat(supervisor.getStudentList().get(0).getFirstName()).isEqualTo("Tea");
    }


    @Test
    public void testUpdate_withValidEntries() throws IdDoesNotExistException {
        Supervisor dummySupervisor = getDummySupervisor();
        when(supervisorRepository.existsById(any())).thenReturn(true);
        when(supervisorRepository.save(any())).thenReturn(dummySupervisor);

        Supervisor actualSupervisor = supervisorService.update(dummySupervisor, dummySupervisor.getId());

        assertThat(actualSupervisor.getMatricule()).isEqualTo(dummySupervisor.getMatricule());
    }

    @Test
    public void testUpdate_withNullID() {
        Supervisor dummySupervisor = getDummySupervisor();

        assertThrows(IllegalArgumentException.class,
                () -> supervisorService.update(dummySupervisor, null));
    }

    @Test
    public void testUpdate_withNullSupervisor() {
        assertThrows(IllegalArgumentException.class,
                () -> supervisorService.update(null, 1L));
    }

    @Test
    public void testUpdate_doesntExistID() {
        Supervisor dummySupervisor = getDummySupervisor();
        when(supervisorRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> supervisorService.update(dummySupervisor, dummySupervisor.getId()));
    }

    @Test
    public void testDelete_withValidID() throws IdDoesNotExistException {
        Long validId = 1L;
        when(supervisorRepository.existsById(any())).thenReturn(true);
        doNothing().when(supervisorRepository).deleteById(any());

        supervisorService.deleteByID(validId);

        verify(supervisorRepository, times(1)).deleteById(any());
    }


    @Test
    public void testDelete_withNullID() {
        assertThrows(IllegalArgumentException.class,
                () -> supervisorService.deleteByID(null));
    }

    @Test
    public void testDelete_doesntExistID() {
        Long id = 1L;
        when(supervisorRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> supervisorService.deleteByID(id));
    }

    @Test
    public void testSupervisorByEmailAndPassword_withValidEntries() throws EmailAndPasswordDoesNotExistException {
        Supervisor dummySupervisor = getDummySupervisor();
        when(supervisorRepository.existsByEmailAndPassword(dummySupervisor.getEmail(), dummySupervisor.getPassword())).thenReturn(true);
        when(supervisorRepository.findSupervisorByEmailAndPassword(dummySupervisor.getEmail(), dummySupervisor.getPassword())).thenReturn(dummySupervisor);

        Supervisor actualSupervisor = supervisorService.getOneByEmailAndPassword(dummySupervisor.getEmail(), dummySupervisor.getPassword());

        assertThat(actualSupervisor.getMatricule()).isEqualTo(dummySupervisor.getMatricule());
    }

    @Test
    public void testSupervisorByEmailAndPassword_withNullEmail() {
        Supervisor dummySupervisor = getDummySupervisor();

        assertThrows(IllegalArgumentException.class,
                () -> supervisorService.getOneByEmailAndPassword(null, dummySupervisor.getPassword()));
    }

    @Test
    public void testSupervisorByEmailAndPassword_withNullPassword() {
        Supervisor dummySupervisor = getDummySupervisor();

        assertThrows(IllegalArgumentException.class,
                () -> supervisorService.getOneByEmailAndPassword(dummySupervisor.getEmail(), null));
    }

    @Test
    public void testSupervisorByEmailAndPassword_doesntExistEmailAndPassword() {
        Supervisor dummySupervisor = getDummySupervisor();
        when(supervisorRepository.existsByEmailAndPassword(any(), any())).thenReturn(false);

        assertThrows(EmailAndPasswordDoesNotExistException.class,
                () -> supervisorService.getOneByEmailAndPassword(dummySupervisor.getEmail(), dummySupervisor.getPassword()));
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

    private List<Supervisor> getDummySupervisorList() {
        List<Supervisor> dummySupervisorList = new ArrayList<>();
        for (long id = 0; id < 3; id++) {
            Supervisor dummySupervisor = getDummySupervisor();
            dummySupervisor.setId(id);
            dummySupervisorList.add(dummySupervisor);
        }
        return dummySupervisorList;
    }
}
