package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.SupervisorAlreadyExistsException;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupervisorServiceTest {

    @InjectMocks
    SupervisorService supervisorService;

    @Mock
    SupervisorRepository supervisorRepository;

    @Test
    public void testCreate_withValidSupervisor() throws SupervisorAlreadyExistsException {
        Supervisor supervisor = getSupervisor();
        when(supervisorRepository.save(any())).thenReturn(supervisor);

        Supervisor actual = supervisorService.create(supervisor);

        assertThat(actual.getEmail()).isEqualTo(supervisor.getEmail());
    }

    private Supervisor getSupervisor() {
        Supervisor supervisor = new Supervisor();
        supervisor.setId(1L);
        supervisor.setLastName("Keys");
        supervisor.setFirstName("Harold");
        supervisor.setEmail("keyh@gmail.com");
        supervisor.setPassword("galaxy29");
        supervisor.setDepartment("Comptabilité");
        supervisor.setMatricule("04736");
        return supervisor;
    }

    @Test
    public void testCreate_withNullSupervisor() {
        assertThrows(IllegalArgumentException.class, () -> {
            supervisorService.create(null);
        });
    }

    @Test
    public void testCreate_alreadyExistsStudent() {
        when(supervisorRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(SupervisorAlreadyExistsException.class, () -> {
            supervisorService.create(getSupervisor());
        });
    }

    @Test
    public void testGetByID_withValidID() throws IdDoesNotExistException {
        Long validID = 1L;
        Supervisor supervisor = getSupervisor();
        when(supervisorRepository.existsById(any())).thenReturn(true);
        when(supervisorRepository.getById(any())).thenReturn(supervisor);

        Supervisor actual = supervisorService.getOneByID(validID);

        assertThat(actual.getMatricule()).isEqualTo(supervisor.getMatricule());
    }

    @Test
    public void testGetByID_withNullID() {
        assertThrows(IllegalArgumentException.class, () -> {
            supervisorService.getOneByID(null);
        });
    }

    @Test
    public void testGetByID_doesntExistID() {
        Supervisor supervisor = getSupervisor();
        when(supervisorRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class, () -> {
            supervisorService.getOneByID(supervisor.getId());
        });
    }

    @Test
    public void testGetAll() {
        when(supervisorRepository.findAll()).thenReturn(getListOfSupervisors());

        final List<Supervisor> allSupervisors = supervisorService.getAll();

        assertThat(allSupervisors.size()).isEqualTo(3);
        assertThat(allSupervisors.get(0).getFirstName()).isEqualTo("Harold");
    }

    private List<Supervisor> getListOfSupervisors() {
        List<Supervisor> supervisorList = new ArrayList<>();
        Long idIterator = 1L;
        for (int i = 0; i < 3; i++) {
            Supervisor supervisor = getSupervisor();
            supervisor.setId(idIterator);
            supervisorList.add(supervisor);
            idIterator++;
        }
        return supervisorList;
    }

    @Test
    public void testUpdate_withValidEntries() throws IdDoesNotExistException {
        Supervisor supervisor = getSupervisor();
        when(supervisorRepository.existsById(any())).thenReturn(true);
        when(supervisorRepository.save(any())).thenReturn(supervisor);

       Supervisor actual = supervisorService.update(supervisor, supervisor.getId());

        assertThat(actual.getMatricule()).isEqualTo(supervisor.getMatricule());
    }

    @Test
    public void testUpdate_withNullID() {
        Supervisor supervisor = getSupervisor();

        assertThrows(IllegalArgumentException.class, () -> {
            supervisorService.update(supervisor, null);
        });
    }

    @Test
    public void testUpdate_withNullSupervisor() {
        assertThrows(IllegalArgumentException.class, () -> {
            supervisorService.update(null, 1L);
        });
    }

    @Test
    public void testUpdate_doesntExistID() {
        Supervisor supervisor = getSupervisor();
        when(supervisorRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class, () -> {
            supervisorService.update(supervisor, supervisor.getId());
        });
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
        assertThrows(IllegalArgumentException.class, () -> {
            supervisorService.deleteByID(null);
        });
    }

    @Test
    public void testDelete_doesntExistID() {
        Long id = 1L;
        when(supervisorRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class, () -> {
            supervisorService.deleteByID(id);
        });
    }

    @Test
    public void testSupervisorByEmailAndPassword_withValidEntries() throws EmailAndPasswordDoesNotExistException {
        Supervisor supervisor = getSupervisor();
        when(supervisorRepository.existsByEmailAndPassword(supervisor.getEmail(), supervisor.getPassword()))
                .thenReturn(true);
        when(supervisorRepository.findSupervisorByEmailAndPassword(supervisor.getEmail(), supervisor.getPassword()))
                .thenReturn(supervisor);

        Supervisor actual = supervisorService.getOneByEmailAndPassword(supervisor.getEmail(), supervisor.getPassword());

        assertThat(actual.getMatricule()).isEqualTo(supervisor.getMatricule());
    }

    @Test
    public void testSupervisorByEmailAndPassword_withNullEmail() {
        Supervisor supervisor = getSupervisor();

        assertThrows(IllegalArgumentException.class, () -> {
            supervisorService.getOneByEmailAndPassword(null, supervisor.getPassword());
        });
    }

    @Test
    public void testSupervisorByEmailAndPassword_withNullPassword() {
        Supervisor supervisor = getSupervisor();

        assertThrows(IllegalArgumentException.class, () -> {
            supervisorService.getOneByEmailAndPassword(supervisor.getEmail(), null);
        });
    }

    @Test
    public void testSupervisorByEmailAndPassword_doesntExistEmailAndPassword() {
        Supervisor supervisor = getSupervisor();
        when(supervisorRepository.existsByEmailAndPassword(any(), any())).thenReturn(false);

        assertThrows(EmailAndPasswordDoesNotExistException.class, () -> {
            supervisorService.getOneByEmailAndPassword(supervisor.getEmail(), supervisor.getPassword());
        });
    }
}
