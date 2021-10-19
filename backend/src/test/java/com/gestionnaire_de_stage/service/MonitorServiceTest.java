package com.gestionnaire_de_stage.service;

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
        Monitor monitor = getMonitor();
        when(monitorRepository.save(any())).thenReturn(monitor);

        Monitor actual = monitorService.create(monitor);

        assertThat(actual).isEqualTo(monitor);
    }

    @Test
    public void testCreate_withNullMonitor() {
        assertThrows(IllegalArgumentException.class,
            () -> monitorService.create(null)
        );
    }

    @Test
    public void testCreate_alreadyExistsMonitor() {
        when(monitorRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(MonitorAlreadyExistsException.class,
            () -> monitorService.create(getMonitor())
        );
    }

    @Test
    public void testGetByID_withValidID() throws Exception {
        Long validID = 1L;
        Monitor monitor = getMonitor();
        when(monitorRepository.existsById(any())).thenReturn(true);
        when(monitorRepository.getById(any())).thenReturn(monitor);

        Monitor actual = monitorService.getOneByID(validID);

        assertThat(actual).isEqualTo(monitor);
    }

    @Test
    public void testGetByID_withNullID() {
        assertThrows(IllegalArgumentException.class,
            () -> monitorService.getOneByID(null)
        );
    }

    @Test
    public void testGetByID_doesntExistID() {
        Long invalidID = 5L;
        when(monitorRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
            () -> monitorService.getOneByID(invalidID)
        );
    }

    @Test
    public void testGetAll() {
        when(monitorRepository.findAll()).thenReturn(getListOfMonitors());

        List<Monitor> actualList = monitorService.getAll();

        assertThat(actualList.size()).isEqualTo(getListOfMonitors().size());
    }

    @Test
    public void testUpdate_withValidEntries() throws Exception {
        Monitor monitor = getMonitor();
        when(monitorRepository.existsById(any())).thenReturn(true);
        when(monitorRepository.save(any())).thenReturn(monitor);

        Monitor actual = monitorService.update(monitor, monitor.getId());

        assertThat(actual).isEqualTo(monitor);
    }

    @Test
    public void testUpdate_withNullID() {
        assertThrows(IllegalArgumentException.class,
            () -> monitorService.update(getMonitor(), null)
        );
    }

    @Test
    public void testUpdate_withNullMonitor() {
        assertThrows(IllegalArgumentException.class,
            () -> monitorService.update(null, 1L)
        );
    }

    @Test
    public void testUpdate_doesntExistID() {
        Monitor monitor = getMonitor();
        when(monitorRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
            () -> monitorService.update(monitor, monitor.getId())
        );
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
            () -> monitorService.deleteByID(null)
        );
    }

    @Test
    public void testDelete_doesntExistID() {
        Long invalidID = 5L;
        when(monitorRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
            () -> monitorService.deleteByID(invalidID)
        );
    }

    @Test
    public void testGetOneByEmailAndPassword_withValidEntries() throws EmailAndPasswordDoesNotExistException {
        Monitor monitor = getMonitor();
        when(monitorRepository.existsByEmailAndPassword(monitor.getEmail(), monitor.getPassword()))
                .thenReturn(true);
        when(monitorRepository.findMonitorByEmailAndPassword(monitor.getEmail(), monitor.getPassword()))
                .thenReturn(monitor);

        Monitor actual = monitorService.getOneByEmailAndPassword(monitor.getEmail(), monitor.getPassword());

        assertThat(actual).isEqualTo(monitor);
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullEmail() {
        Monitor monitor = getMonitor();

        assertThrows(IllegalArgumentException.class,
            () -> monitorService.getOneByEmailAndPassword(null, monitor.getPassword())
        );
    }

    @Test
    public void testGetOneByEmailAndPassword_withNullPassword() {
        Monitor monitor = getMonitor();

        assertThrows(IllegalArgumentException.class,
            () -> monitorService.getOneByEmailAndPassword(monitor.getEmail(), null)
        );
    }

    @Test
    public void testGetOneByEmailAndPassword_doesntExistEmailAndPassword() {
        Monitor monitor = getMonitor();
        when(monitorRepository.existsByEmailAndPassword(any(), any())).thenReturn(false);

        assertThrows(EmailAndPasswordDoesNotExistException.class,
            () -> monitorService.getOneByEmailAndPassword(monitor.getEmail(), monitor.getPassword())
        );
    }

    @Test
    public void testFindMonitorByEmail_withValidEmail() {
        Monitor monitor = getMonitor();
        String email = "stepotato@gmail.com";
        when(monitorRepository.getMonitorByEmail(any())).thenReturn(monitor);

        Monitor actual = monitorService.getOneByEmail(email);

        assertThat(actual.getPostalCode()).isEqualTo(monitor.getPostalCode());
    }

    @Test
    public void testFindMonitorByEmail_withNullEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> monitorService.getOneByEmail(null)
        );
    }

    private Monitor getMonitor() {
        Monitor monitor = new Monitor();
        monitor.setId(1L);
        monitor.setLastName("toto");
        monitor.setFirstName("titi");
        monitor.setEmail("toto@gmail.com");
        monitor.setPassword("testPassword");
        return monitor;
    }

    private List<Monitor> getListOfMonitors() {
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
