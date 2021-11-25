package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.exception.DoesNotExistException;
import com.gestionnaire_de_stage.exception.EmailAndPasswordDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.MonitorAlreadyExistsException;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.repository.MonitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MonitorServiceTest {

    @InjectMocks
    private MonitorService monitorService;

    @Mock
    private MonitorRepository monitorRepository;

    @Test
    public void testCreate_withValidMonitor() throws Exception {
        Monitor dummyMonitor = getDummyMonitor();
        when(monitorRepository.save(any())).thenReturn(dummyMonitor);
        when(monitorRepository.existsByEmail(any())).thenReturn(false);

        Monitor actualMonitor = monitorService.create(dummyMonitor);

        assertThat(actualMonitor).isEqualTo(dummyMonitor);
    }

    @Test
    public void testCreate_withNullMonitor() {
        assertThrows(IllegalArgumentException.class,
                () -> monitorService.create(null));
    }

    @Test
    public void testCreate_alreadyExistsMonitor() {
        when(monitorRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(MonitorAlreadyExistsException.class,
                () -> monitorService.create(getDummyMonitor()));
    }

    @Test
    public void testGetByID_withValidID() throws Exception {
        Long validID = 1L;
        Monitor dummyMonitor = getDummyMonitor();
        when(monitorRepository.existsById(any())).thenReturn(true);
        when(monitorRepository.getById(any())).thenReturn(dummyMonitor);

        Monitor actualMonitor = monitorService.getOneByID(validID);

        assertThat(actualMonitor).isEqualTo(dummyMonitor);
    }

    @Test
    public void testGetByID_withNullID() {
        assertThrows(IllegalArgumentException.class,
                () -> monitorService.getOneByID(null));
    }

    @Test
    public void testGetByID_doesntExistID() {
        Long invalidID = 5L;
        when(monitorRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> monitorService.getOneByID(invalidID));
    }

    @Test
    public void testGetAll() {
        when(monitorRepository.findAll()).thenReturn(getDummyMonitorList());

        List<Monitor> actualMonitorList = monitorService.getAll();

        assertThat(actualMonitorList.size()).isEqualTo(getDummyMonitorList().size());
    }

    @Test
    public void testUpdate_withValidEntries() throws Exception {
        Monitor dummyMonitor = getDummyMonitor();
        when(monitorRepository.existsById(any())).thenReturn(true);
        when(monitorRepository.save(any())).thenReturn(dummyMonitor);

        Monitor actualMonitor = monitorService.update(dummyMonitor);

        assertThat(actualMonitor).isEqualTo(dummyMonitor);
    }


    @Test
    @SuppressWarnings("ConstantConditions")
    public void testUpdate_withNullMonitor() {
        assertThrows(IllegalArgumentException.class,
                () -> monitorService.update(null));
    }

    @Test
    public void testUpdate_doesntExistID() {
        Monitor dummyMonitor = getDummyMonitor();
        when(monitorRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> monitorService.update(dummyMonitor));
    }

    @Test
    public void testDelete_withValidID() throws Exception {
        Long validID = 1L;
        when(monitorRepository.existsById(any())).thenReturn(true);
        doNothing().when(monitorRepository).deleteById(validID);

        monitorService.deleteByID(validID);

        verify(monitorRepository, times(1)).deleteById(any());
    }

    @Test
    public void testDelete_withNullID() {
        assertThrows(IllegalArgumentException.class,
                () -> monitorService.deleteByID(null));
    }

    @Test
    public void testDelete_doesntExistID() {
        Long invalidID = 5L;
        when(monitorRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> monitorService.deleteByID(invalidID));
    }

    @Test
    public void testGetOneByEmailAndPassword_withValidEntries() throws EmailAndPasswordDoesNotExistException {
        Monitor dummyMonitor = getDummyMonitor();
        when(monitorRepository.existsByEmailAndPassword(dummyMonitor.getEmail(), dummyMonitor.getPassword())).thenReturn(true);
        when(monitorRepository.findMonitorByEmailAndPassword(dummyMonitor.getEmail(), dummyMonitor.getPassword())).thenReturn(dummyMonitor);

        Monitor actualMonitor = monitorService.getOneByEmailAndPassword(dummyMonitor.getEmail(), dummyMonitor.getPassword());

        assertThat(actualMonitor).isEqualTo(dummyMonitor);
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullEmail() {
        Monitor dummyMonitor = getDummyMonitor();

        assertThrows(IllegalArgumentException.class,
                () -> monitorService.getOneByEmailAndPassword(null, dummyMonitor.getPassword()));
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullPassword() {
        Monitor dummyMonitor = getDummyMonitor();

        assertThrows(IllegalArgumentException.class,
                () -> monitorService.getOneByEmailAndPassword(dummyMonitor.getEmail(), null));
    }

    @Test
    public void testGetOneByEmailAndPassword_doesntExistEmailAndPassword() {
        Monitor dummyMonitor = getDummyMonitor();
        when(monitorRepository.existsByEmailAndPassword(any(), any())).thenReturn(false);

        assertThrows(EmailAndPasswordDoesNotExistException.class,
                () -> monitorService.getOneByEmailAndPassword(dummyMonitor.getEmail(), dummyMonitor.getPassword()));
    }

    @Test
    public void testGetOneByEmail_withValidEmail() throws DoesNotExistException {
        Monitor monitor = getDummyMonitor();
        String email = "stepotato@gmail.com";
        when(monitorRepository.existsByEmail(any())).thenReturn(true);
        when(monitorRepository.getMonitorByEmail(any())).thenReturn(monitor);

        Monitor actual = monitorService.getOneByEmail(email);

        assertThat(actual.getPostalCode()).isEqualTo(monitor.getPostalCode());
    }

    @Test
    public void testGetOneByEmail_withNullEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> monitorService.getOneByEmail(null));
    }

    @Test
    public void testGetOneByEmail_withInvalidEmail() {
        String email = "civfan@email.com";
        assertThrows(DoesNotExistException.class,
                () -> monitorService.getOneByEmail(email),
                "L'email n'existe pas");
    }

    @Test
    public void testIsIdInvalid_whenFalse() {
        when(monitorRepository.existsById(any())).thenReturn(true);

        boolean idInvalid = monitorService.isIdInvalid(1L);

        assertThat(idInvalid).isFalse();
    }

    @Test
    public void testIsIdInvalid_whenTrue() {
        when(monitorRepository.existsById(any())).thenReturn(false);

        boolean idInvalid = monitorService.isIdInvalid(1L);

        assertThat(idInvalid).isTrue();
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

    private List<Monitor> getDummyMonitorList() {
        Monitor monitor1 = new Monitor();
        monitor1.setId(1L);
        monitor1.setFirstName("Steph");
        monitor1.setLastName("Kazuk");
        monitor1.setEmail("stepotato@gmail.com");
        monitor1.setPhone("5145555112");
        monitor1.setDepartment("Informatique");
        monitor1.setPassword("testPassword");

        Monitor monitor2 = new Monitor();
        monitor2.setId(2L);
        monitor2.setFirstName("Ouss");
        monitor2.setLastName("ama");
        monitor2.setEmail("ouste@gmail.com");
        monitor2.setPhone("5145555112");
        monitor2.setDepartment("Informatique");
        monitor2.setPassword("testPassword");

        Monitor monitor3 = new Monitor();
        monitor3.setId(3L);
        monitor3.setFirstName("same");
        monitor3.setLastName("dude");
        monitor3.setEmail("dudesame@gmail.com");
        monitor3.setPhone("5145555112");
        monitor3.setDepartment("Informatique");
        monitor3.setPassword("testPassword");

        return Arrays.asList(monitor1, monitor2, monitor3);
    }


}
