package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.StudentCurriculumsDTO;
import com.gestionnaire_de_stage.dto.ValidationCurriculum;
import com.gestionnaire_de_stage.exception.CurriculumAlreadyTreatedException;
import com.gestionnaire_de_stage.exception.CurriculumUsedException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.Curriculum;
import com.gestionnaire_de_stage.model.Monitor;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.model.Student;
import com.gestionnaire_de_stage.repository.CurriculumRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SuppressWarnings("ConstantConditions")
@ExtendWith(MockitoExtension.class)
public class CurriculumServiceTest {

    @InjectMocks
    private CurriculumService curriculumService;

    @Mock
    private CurriculumRepository curriculumRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private OfferApplicationService offerApplicationService;

    @Test
    public void testConvertMultipartFileToCurriculum_WithValidData() throws IOException, IdDoesNotExistException {
        Student student = new Student();
        student.setId(1L);
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        when(studentService.getOneByID(any())).thenReturn(student);

        Curriculum actualCurriculum = curriculumService.convertMultipartFileToCurriculum(file, student.getId());

        assertThat(actualCurriculum.getStudent()).isEqualTo(student);
    }

    @Test
    public void testConvertMultipartFileToCurriculum_withNullFile() {
        Student student = new Student();

        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.convertMultipartFileToCurriculum(null, student.getId()));
    }

    @Test
    public void testConvertMultipartFileToCurriculum_withNullStudentID() {
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());

        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.convertMultipartFileToCurriculum(file, null));
    }

    @Test
    public void testConvertMultipartFileToCurriculum_doesntExistStudentID() throws Exception {
        Student student = new Student();
        student.setId(1L);
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        when(studentService.getOneByID(any())).thenThrow(IdDoesNotExistException.class);

        assertThrows(IdDoesNotExistException.class,
                () -> curriculumService.convertMultipartFileToCurriculum(file, student.getId()));
    }

    @Test
    public void testCreate_withValidCurriculum() {
        Curriculum dummyCurriculum = getDummyCurriculum();
        when(curriculumRepository.save(any())).thenReturn(dummyCurriculum);

        Curriculum actualCurriculum = curriculumService.create(dummyCurriculum);

        assertThat(actualCurriculum).isEqualTo(dummyCurriculum);
    }

    @Test
    public void testCreate_withNullCurriculum() {
        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.create(null));
    }

    @Test
    public void testGetByID_withValidID() throws Exception {
        long validID = 1L;
        Curriculum dummyCurriculum = getDummyCurriculum();
        when(curriculumRepository.findById(any())).thenReturn(Optional.of(dummyCurriculum));

        Curriculum actualCurriculum = curriculumService.getOneById(validID);

        assertThat(actualCurriculum).isEqualTo(dummyCurriculum);
    }

    @Test
    public void testGetByID_withNullID() {
        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.getOneById(null));
    }

    @Test
    public void testGetByID_doesntExistID() {
        Long invalidID = 5L;
        when(curriculumRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IdDoesNotExistException.class,
                () -> curriculumService.getOneById(invalidID));
    }

    @Test
    public void testFindAllByStudent_withValidStudent() {
        List<Curriculum> curriculumList = getDummyCurriculumValidList();
        Student student = getDummyStudent();
        when(curriculumRepository.findAllByStudent(any())).thenReturn(curriculumList);

        List<Curriculum> actualList = curriculumService.findAllByStudent(student);

        assertThat(actualList).isEqualTo(curriculumList);
    }

    @Test
    public void testFindAllByStudent_withNullStudent() {
        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.findAllByStudent(null));
    }

    @Test
    public void testAllCurriculumsByStudentAsStudentCurriculumsDTO_withValidEntries() {
        Student student = getDummyStudent();
        List<Curriculum> curriculumList = getDummyCurriculumList();
        student.setPrincipalCurriculum(curriculumList.get(0));
        when(curriculumRepository.findAllByStudent(any())).thenReturn(curriculumList);

        StudentCurriculumsDTO actual = curriculumService.allCurriculumsByStudentAsStudentCurriculumsDTO(student);

        assertThat(actual.getCurriculumList()).isEqualTo(curriculumList);
        assertThat(actual.getPrincipal()).isEqualTo(student.getPrincipalCurriculum());
    }

    @Test
    public void testAllCurriculumsByStudentAsStudentCurriculumsDTO_withNullStudent() {
        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.allCurriculumsByStudentAsStudentCurriculumsDTO(null));
    }

    @Test
    public void testAllCurriculumsByStudentAsStudentCurriculumsDTO_withNoPrincipal() {
        Student student = getDummyStudent();
        List<Curriculum> curriculumList = getDummyCurriculumList();
        when(curriculumRepository.findAllByStudent(any())).thenReturn(curriculumList);

        StudentCurriculumsDTO actual = curriculumService.allCurriculumsByStudentAsStudentCurriculumsDTO(student);

        assertThat(actual.getCurriculumList()).isEqualTo(curriculumList);
        assertThat(actual.getPrincipal()).isNull();
    }

    @Test
    public void testValidate() throws Exception {
        Curriculum curriculum = getDummyCurriculum();
        curriculum.setId(1L);
        ValidationCurriculum validationCurriculumDTO = new ValidationCurriculum();
        validationCurriculumDTO.setId(curriculum.getId());
        validationCurriculumDTO.setValid(true);

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(curriculum));

        assertThat(curriculumService.validate(validationCurriculumDTO)).isTrue();
    }

    @Test
    public void testValidate_whenCvNonExistent() {
        Curriculum curriculum = getDummyCurriculum();
        curriculum.setId(1L);
        ValidationCurriculum validationCurriculumDTO = new ValidationCurriculum();
        validationCurriculumDTO.setId(curriculum.getId());
        validationCurriculumDTO.setValid(true);

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IdDoesNotExistException.class,
                () -> curriculumService.validate(validationCurriculumDTO));
    }

    @Test
    public void testValidate_whenCurriculumAlreadyTreated() {
        Curriculum curriculum = getDummyCurriculum();
        curriculum.setId(1L);
        curriculum.setIsValid(true);

        ValidationCurriculum validationCurriculumDTO = new ValidationCurriculum();
        validationCurriculumDTO.setId(curriculum.getId());
        validationCurriculumDTO.setValid(true);

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(curriculum));
        assertThrows(CurriculumAlreadyTreatedException.class, () ->
                curriculumService.validate(validationCurriculumDTO));
    }

    @Test
    public void testValidate_withIdCurriculumNull() {
        ValidationCurriculum validationCurriculumDTO = new ValidationCurriculum();
        validationCurriculumDTO.setId(null);
        validationCurriculumDTO.setValid(true);

        assertThrows(IllegalArgumentException.class, () ->
                curriculumService.validate(validationCurriculumDTO));
    }

    @Test
    void testReject() throws Exception {
        Curriculum dummyCurriculum = getDummyCurriculum();
        dummyCurriculum.setId(1L);
        ValidationCurriculum validationCurriculumDTO = new ValidationCurriculum();
        validationCurriculumDTO.setId(dummyCurriculum.getId());
        validationCurriculumDTO.setValid(false);

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(dummyCurriculum));

        assertThat(curriculumService.validate(validationCurriculumDTO)).isTrue();
    }

    @Test
    void testReject_whenCvNonExistent() {
        Curriculum dummyCurriculum = getDummyCurriculum();
        dummyCurriculum.setId(1L);
        ValidationCurriculum validationCurriculumDTO = new ValidationCurriculum();
        validationCurriculumDTO.setId(dummyCurriculum.getId());
        validationCurriculumDTO.setValid(false);

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IdDoesNotExistException.class, () ->
                curriculumService.validate(validationCurriculumDTO));
    }

    @Test
    void testReject_whenCurriculumAlreadyTreated() {
        Curriculum dummyCurriculum = getDummyCurriculum();
        dummyCurriculum.setId(1L);
        dummyCurriculum.setIsValid(true);
        ValidationCurriculum validationCurriculumDTO = new ValidationCurriculum();
        validationCurriculumDTO.setId(dummyCurriculum.getId());
        validationCurriculumDTO.setValid(false);

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(dummyCurriculum));

        assertThrows(CurriculumAlreadyTreatedException.class, () ->
                curriculumService.validate(validationCurriculumDTO));
    }

    @Test
    void testReject_withIdCurriculumNull() {
        ValidationCurriculum validationCurriculumDTO = new ValidationCurriculum();
        validationCurriculumDTO.setId(null);
        validationCurriculumDTO.setValid(false);

        assertThrows(IllegalArgumentException.class, () ->
                curriculumService.validate(validationCurriculumDTO));
    }

    @Test
    void testFindOneById() throws Exception {
        Curriculum dummyCurriculum = getDummyCurriculum();
        dummyCurriculum.setId(1L);
        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(dummyCurriculum));

        Curriculum actualCurriculum = curriculumService.getOneById(dummyCurriculum.getId());

        assertThat(actualCurriculum).isEqualTo(dummyCurriculum);
    }

    @Test
    public void testFindOneById_withIdNull() {
        assertThrows(IllegalArgumentException.class, () ->
                curriculumService.getOneById(null));
    }

    @Test
    public void testFindOneById_withCurriculumNonExistent() {
        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IdDoesNotExistException.class, () ->
                curriculumService.getOneById(34L));
    }

    @Test
    public void testFindAllByStudentId() throws Exception {
        Student dummyStudent = getDummyStudent();
        List<Curriculum> dummyCurriculumList = getDummyCurriculumList();
        when(studentService.getOneByID(anyLong())).thenReturn(dummyStudent);
        when(curriculumRepository.findAllByStudent(any())).thenReturn(dummyCurriculumList);

        List<Curriculum> actualCurriculums = curriculumService.findAllByStudentId(dummyStudent.getId());

        assertThat(actualCurriculums).isEqualTo(dummyCurriculumList);
    }

    @Test
    public void testDeleteOneById() throws Exception {
        when(curriculumRepository.findById(any())).thenReturn(Optional.of(getDummyCurriculum()));
        when(offerApplicationService.isCurriculumInUse(any())).thenReturn(false);

        curriculumService.deleteOneById(1L);

        verify(curriculumRepository, times(1)).deleteById(any());
    }

    @Test
    public void testDeleteOneById_withNullParam() {
        assertThrows(IllegalArgumentException.class, () ->
                curriculumService.deleteOneById(null));
    }

    @Test
    public void testDeleteOneById_withPrincipalCurriculum() {
        Curriculum curriculum = getDummyCurriculum();
        curriculum.getStudent().setPrincipalCurriculum(curriculum);
        when(curriculumRepository.findById(any())).thenReturn(Optional.of(curriculum));

        assertThrows(CurriculumUsedException.class, () ->
                curriculumService.deleteOneById(1L));
    }

    @Test
    public void testDeleteOneById_withCurriculumInUse() {
        when(curriculumRepository.findById(any())).thenReturn(Optional.of(getDummyCurriculum())) ;
        when(offerApplicationService.isCurriculumInUse(any())).thenReturn(true);

        assertThrows(CurriculumUsedException.class, () ->
                curriculumService.deleteOneById(1L));
    }

    private Curriculum getDummyCurriculum() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);

        return new Curriculum(
                "fileName",
                "content type",
                "test".getBytes(),
                dummyStudent
        );
    }

    private List<Curriculum> getDummyCurriculumList() {
        Curriculum dummyCurriculum1 = getDummyCurriculum();
        Curriculum dummyCurriculum2 = getDummyCurriculum();
        Curriculum dummyCurriculum3 = getDummyCurriculum();

        return Arrays.asList(dummyCurriculum1, dummyCurriculum2, dummyCurriculum3);
    }

    private Curriculum getDummyCurriculumOffer() {
        Curriculum dummyCurriculum = new Curriculum();

        dummyCurriculum.setId(1L);
        dummyCurriculum.setData("some xml".getBytes());
        dummyCurriculum.setName("fileeeename");
        dummyCurriculum.setStudent(getDummyStudent());
        return dummyCurriculum;
    }

    private Student getDummyStudent() {
        Student dummyStudent = new Student();
        dummyStudent.setId(1L);
        dummyStudent.setLastName("Winter");
        dummyStudent.setFirstName("Summer");
        dummyStudent.setEmail("cant@outlook.com");
        dummyStudent.setPassword("cantPass");
        dummyStudent.setDepartment("info");
        dummyStudent.setMatricule("4673943");
        return dummyStudent;
    }

    private Offer getDummyOffer() {
        Offer dummyOffer = new Offer();
        dummyOffer.setDepartment("Un departement");
        dummyOffer.setAddress("ajsaodas");
        dummyOffer.setId(1L);
        dummyOffer.setDescription("oeinoiendw");
        dummyOffer.setSalary(10);
        dummyOffer.setTitle("oeinoiendw");
        dummyOffer.setCreator(getDummyMonitor());
        return dummyOffer;
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

    private List<Curriculum> getDummyCurriculumValidList() {
        Curriculum dummyCurriculum1 = getDummyCurriculum();
        Curriculum dummyCurriculum2 = getDummyCurriculum();
        Curriculum dummyCurriculum3 = getDummyCurriculum();
        dummyCurriculum1.setIsValid(true);
        dummyCurriculum2.setIsValid(true);
        dummyCurriculum3.setIsValid(true);
        return Arrays.asList(dummyCurriculum1, dummyCurriculum2, dummyCurriculum3);
    }
}
