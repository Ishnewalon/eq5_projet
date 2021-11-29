package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.ManagerAlreadyExistsException;
import com.gestionnaire_de_stage.model.Manager;
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
        Manager dummyManager = getDummyManager();
        when(managerRepository.save(any())).thenReturn(dummyManager);

        Manager actualManager = managerService.create(dummyManager);

        assertThat(actualManager.getEmail()).isEqualTo(dummyManager.getEmail());
    }

    @Test
    public void testCreate_withNullManager() {
        assertThrows(IllegalArgumentException.class,
                () -> managerService.create(null));
    }

    @Test
    public void testCreate_alreadyExistsManager() {
        when(managerRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(ManagerAlreadyExistsException.class,
                () -> managerService.create(getDummyManager()));
    }

    @Test
    public void testGetByID_withValidID() throws IdDoesNotExistException {
        Long validID = 1L;
        Manager dummyManager = getDummyManager();
        when(managerRepository.existsById(validID)).thenReturn(true);
        when(managerRepository.getById(any())).thenReturn(dummyManager);

        Manager actualManager = managerService.getOneByID(validID);

        assertThat(actualManager.getEmail()).isEqualTo(dummyManager.getEmail());
    }

    @Test
    public void testGetByID_withNullID() {
        assertThrows(IllegalArgumentException.class,
                () -> managerService.getOneByID(null));
    }

    @Test
    public void testGetByID_doesntExistID() {
        Manager dummyManager = getDummyManager();
        dummyManager.setId(1L);
        when(managerRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> managerService.getOneByID(dummyManager.getId()));
    }

    @Test
    public void testGetAll() {
        when(managerRepository.findAll()).thenReturn(getDummyManagerList());

        final List<Manager> actualManagerList = managerService.getAll();

        assertThat(actualManagerList.size()).isEqualTo(3);
        assertThat(actualManagerList.get(0).getFirstName()).isEqualTo("Oussama");
    }

    @Test
    public void testUpdate_withValidEntries() throws IdDoesNotExistException {
        Manager dummyManager = getDummyManager();
        dummyManager.setId(2L);
        when(managerRepository.existsById(any())).thenReturn(true);
        when(managerRepository.save(any())).thenReturn(dummyManager);

        Manager actualManager = managerService.update(dummyManager, dummyManager.getId());

        assertThat(actualManager.getEmail()).isEqualTo(dummyManager.getEmail());
    }

    @Test
    public void testUpdate_withNullID() {
        Manager dummyManager = getDummyManager();

        assertThrows(IllegalArgumentException.class,
                () -> managerService.update(dummyManager, null));
    }

    @Test
    public void testUpdate_withNullStudent() {
        assertThrows(IllegalArgumentException.class,
                () -> managerService.update(null, 1L));
    }

    @Test
    public void testUpdate_doesntExistID() {
        Manager dummyManager = getDummyManager();
        dummyManager.setId(1L);
        when(managerRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> managerService.update(dummyManager, dummyManager.getId()));
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
                () -> managerService.deleteByID(null));
    }

    @Test
    public void testDelete_doesntExistID() {
        Long id = 1L;
        when(managerRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> managerService.deleteByID(id));
    }

    @Test
    public void testGetOneByEmailAndPassword_withValidEntries() throws EmailAndPasswordDoesNotExistException {
        Manager dummyManager = getDummyManager();
        when(managerRepository.existsByEmailAndPassword(any(), any())).thenReturn(true);
        when(managerRepository.findManagerByEmailAndPassword(any(), any())).thenReturn(dummyManager);

        Manager actualManager = managerService.getOneByEmailAndPassword(dummyManager.getEmail(), dummyManager.getPassword());

        assertThat(actualManager.getFirstName()).isEqualTo(dummyManager.getFirstName());
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullEmail() {
        Manager dummyManager = getDummyManager();

        assertThrows(IllegalArgumentException.class,
                () -> managerService.getOneByEmailAndPassword(null, dummyManager.getPassword()));
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullPassword() {
        Manager dummyManager = getDummyManager();

        assertThrows(IllegalArgumentException.class,
                () -> managerService.getOneByEmailAndPassword(dummyManager.getEmail(), null));
    }

    @Test
    public void testGetOneByEmailAndPassword_doesntExistEmailAndPassword() {
        Manager dummyManager = getDummyManager();
        when(managerRepository.existsByEmailAndPassword(any(), any())).thenReturn(false);

        assertThrows(EmailAndPasswordDoesNotExistException.class,
                () -> managerService.getOneByEmailAndPassword(dummyManager.getEmail(), dummyManager.getPassword()));
    }

    @Test
    public void testCheckEmailValidity() {
        String email = "oussamakably@gmail.com";
        when(managerRepository.existsByEmail(any())).thenReturn(true);

        boolean isValid = managerService.isEmailInvalid(email);

        assertThat(isValid).isFalse();
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

    private List<Manager> getDummyManagerList() {
        List<Manager> dummyManagerList = new ArrayList<>();
        for (long id = 0; id < 3; id++) {
            Manager dummyManager = getDummyManager();
            dummyManager.setId(id);
            dummyManagerList.add(dummyManager);
        }
        return dummyManagerList;
    }
}