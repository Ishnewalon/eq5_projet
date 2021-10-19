package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.ManagerAlreadyExistsException;
import com.gestionnaire_de_stage.model.Manager;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {

    @InjectMocks
    private ManagerService managerService;

    @Mock
    private ManagerRepository managerRepository;

    @Test
    public void testCreate_withValidManager() throws ManagerAlreadyExistsException {
        Manager manager = getManager();
        when(managerRepository.save(any())).thenReturn(manager);

        Manager actual = managerService.create(manager);

        assertThat(actual.getEmail()).isEqualTo(manager.getEmail());
    }

    @Test
    public void testCreate_withNullManager() {
        assertThrows(IllegalArgumentException.class,
            () -> managerService.create(null)
        );
    }

    @Test
    public void testCreate_alreadyExistsManager() {
        when(managerRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(ManagerAlreadyExistsException.class,
            () -> managerService.create(getManager())
        );
    }

    @Test
    public void testGetByID_withValidID() throws IdDoesNotExistException {
        Long validID = 1L;
        Manager manager = getManager();
        when(managerRepository.existsById(validID)).thenReturn(true);
        when(managerRepository.getById(any())).thenReturn(manager);

        Manager actual = managerService.getOneByID(validID);

        assertThat(actual.getEmail()).isEqualTo(manager.getEmail());
    }

    @Test
    public void testGetByID_withNullID() {
        assertThrows(IllegalArgumentException.class,
            () -> managerService.getOneByID(null)
        );
    }

    @Test
    public void testGetByID_doesntExistID() {
        Manager manager = getManager();
        manager.setId(1L);
        when(managerRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
            () -> managerService.getOneByID(manager.getId())
        );
    }

    @Test
    public void testGetAll() {
        when(managerRepository.findAll()).thenReturn(getListOfManagers());

        final List<Manager> allManagers = managerService.getAll();

        assertThat(allManagers.size()).isEqualTo(3);
        assertThat(allManagers.get(0).getFirstName()).isEqualTo("Oussama");
    }

    @Test
    public void testUpdate_withValidEntries() throws IdDoesNotExistException {
        Manager manager = getManager();
        manager.setId(2L);
        when(managerRepository.existsById(any())).thenReturn(true);
        when(managerRepository.save(any())).thenReturn(manager);

        Manager actual = managerService.update(manager, manager.getId());

        assertThat(actual.getEmail()).isEqualTo(manager.getEmail());
    }

    @Test
    public void testUpdate_withNullID() {
        Manager manager = getManager();

        assertThrows(IllegalArgumentException.class,
            () -> managerService.update(manager, null)
        );
    }

    @Test
    public void testUpdate_withNullStudent() {
        assertThrows(IllegalArgumentException.class,
            () -> managerService.update(null, 1L)
        );
    }

    @Test
    public void testUpdate_doesntExistID() {
        Manager manager = getManager();
        manager.setId(1L);
        when(managerRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
            () -> managerService.update(manager, manager.getId())
        );
    }

    @Test
    public void testDelete_withValidID() throws IdDoesNotExistException {
        Long validID = 1L;
        when(managerRepository.existsById(any())).thenReturn(true);
        doNothing().when(managerRepository).deleteById(any());

        managerService.deleteByID(validID);

        verify(managerRepository, times(1)).deleteById(any());
    }

    @Test
    public void testDelete_withNullID() {
        assertThrows(IllegalArgumentException.class,
            () -> managerService.deleteByID(null)
        );
    }

    @Test
    public void testDelete_doesntExistID() {
        Long id = 1L;
        when(managerRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
            () -> managerService.deleteByID(id)
        );
    }

    @Test
    public void testGetOneByEmailAndPassword_withValidEntries() throws EmailAndPasswordDoesNotExistException {
        Manager manager = getManager();
        when(managerRepository.existsByEmailAndPassword(any(), any()))
                .thenReturn(true);
        when(managerRepository.findManagerByEmailAndPassword(any(), any()))
                .thenReturn(manager);

        Manager actual = managerService.getOneByEmailAndPassword(manager.getEmail(), manager.getPassword());

        assertThat(actual.getFirstName()).isEqualTo(manager.getFirstName());
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullEmail() {
        Manager manager = getManager();

        assertThrows(IllegalArgumentException.class,
            () -> managerService.getOneByEmailAndPassword(null, manager.getPassword())
        );
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullPassword() {
        Manager manager = getManager();

        assertThrows(IllegalArgumentException.class,
            () -> managerService.getOneByEmailAndPassword(manager.getEmail(), null)
        );
    }

    @Test
    public void testGetOneByEmailAndPassword_doesntExistEmailAndPassword() {
        Manager manager = getManager();
        when(managerRepository.existsByEmailAndPassword(any(), any())).thenReturn(false);

        assertThrows(EmailAndPasswordDoesNotExistException.class,
            () -> managerService.getOneByEmailAndPassword(manager.getEmail(), manager.getPassword())
        );
    }

    private Manager getManager() {
        Manager manager = new Manager();
        manager.setPassword("Test1234");
        manager.setEmail("oussamakably@gmail.com");
        manager.setFirstName("Oussama");
        manager.setLastName("Kably");
        manager.setPhone("5143643320");
        return manager;
    }

    private List<Manager> getListOfManagers() {
        List<Manager> managerList = new ArrayList<>();
        for (long id = 0; id < 3; id++) {
            Manager manager = getManager();
            manager.setId(id);
            managerList.add(manager);
        }
        return managerList;
    }
}