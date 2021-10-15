package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.SupervisorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.model.Supervisor;
import com.gestionnaire_de_stage.repository.SupervisorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    /*
    @Test
    public void testDelete_withValidID() {
        Long validId = 1L;

        boolean actual = supervisorService.deleteByID(validId);

        assertTrue(actual);
    }

    @Test
    public void testDelete_withNullID() {
        boolean actual = supervisorService.deleteByID(null);

        assertFalse(actual);
    }

    @Test
    public void testFindSupervisorByEmailAndPassword() {
        String email = "keyh@gmail.com";
        String password = "galaxy29";

        Supervisor supervisor = supervisorRepository.findSupervisorByEmailAndPassword(email, password);
        String actual = supervisor.getFirstName();

        assertEquals(actual, "Harold");
    }

    @Test
    public void testExistsByEmailAndPassword_withValidEntries() {
        String email = "keyh@gmail.com";
        String password = "galaxy29";

        boolean actual = supervisorRepository.existsByEmailAndPassword(email, password);

        assertTrue(actual);
    }

    @Test
    public void testExistsByEmailAndPassword_withNullEntries() {
        boolean actual = supervisorRepository.existsByEmailAndPassword(null, null);

        assertFalse(actual);
    }

    @Test
    public void testGetOneByEmailAndPassword_withValidEntries() {
        String email = "keyh@gmail.com";
        String password = "galaxy29";

        Optional<Supervisor> actual = supervisorService.getOneByEmailAndPassword(email, password);

        assertTrue(actual.isPresent());
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullEntries() {
        Optional<Supervisor> actual = supervisorService.getOneByEmailAndPassword(null, null);

        assertTrue(actual.isEmpty());
    }
    */
}
