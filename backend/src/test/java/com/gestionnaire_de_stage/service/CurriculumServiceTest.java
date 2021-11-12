package com.gestionnaire_de_stage.service;

import com.gestionnaire_de_stage.dto.CurriculumDTO;
import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.dto.StudentCurriculumsDTO;
import com.gestionnaire_de_stage.exception.CurriculumAlreadyTreatedException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.model.*;
import com.gestionnaire_de_stage.repository.CurriculumRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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
    private OfferService offerService;

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
    public void testCreate_withValidCurriculum() throws IdDoesNotExistException {
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
        Long validID = 1L;
        Curriculum dummyCurriculum = getDummyCurriculum();
        when(curriculumRepository.existsById(any())).thenReturn(true);
        when(curriculumRepository.getById(any())).thenReturn(dummyCurriculum);

        Curriculum actualCurriculum = curriculumService.getOneByID(validID);

        assertThat(actualCurriculum).isEqualTo(dummyCurriculum);
    }

    @Test
    public void testGetByID_withNullID() {
        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.getOneByID(null));
    }

    @Test
    public void testGetByID_doesntExistID() {
        Long invalidID = 5L;
        when(curriculumRepository.existsById(any())).thenReturn(false);

        assertThrows(IdDoesNotExistException.class,
                () -> curriculumService.getOneByID(invalidID));
    }

    @Test
    public void testGetAll() {
        when(curriculumRepository.findAll()).thenReturn(getDummyCurriculumList());

        List<Curriculum> actualCurriculumList = curriculumService.getAll();

        assertThat(actualCurriculumList.size()).isGreaterThan(0);
    }

    @Test
    public void testMapToCurriculumDTOList_withValidEntries() {
        List<OfferApplication> offerApplicationList = getDummyOfferAppList();
        List<CurriculumDTO> curriculumDTOList = getDummyCurriculumDTOList();
        when(offerService.mapToOfferDTO(any())).thenReturn(getDummyOfferDto());

        List<CurriculumDTO> actualCurriculumDTOList = curriculumService.mapToCurriculumDTOList(offerApplicationList);

        assertThat(actualCurriculumDTOList.size()).isEqualTo(curriculumDTOList.size());
        assertThat(actualCurriculumDTOList.get(1).getFirstName()).isEqualTo(curriculumDTOList.get(1).getFirstName());
    }

    @Test
    public void testFindAllCurriculumWithInvalidCurriculum() {
        List<Curriculum> dummyCurriculumList = getDummyCurriculumList();
        when(curriculumRepository.findAllByIsValidIsNull()).thenReturn(dummyCurriculumList);

        List<Curriculum> curriculumList = curriculumService.findAllCurriculumNotValidatedYet();

        assertThat(curriculumList).isEqualTo(dummyCurriculumList);
    }

    @Test
    public void testFindAllCurriculumsWithInvalidCurriculum_withEmptyList() {
        when(curriculumRepository.findAllByIsValidIsNull()).thenReturn(Collections.emptyList());

        List<Curriculum> curriculumList = curriculumService.findAllCurriculumNotValidatedYet();

        assertThat(curriculumList).isEmpty();
    }

    @Test
    public void testFindAllCurriculumWithValidCurricculum() {
        List<Curriculum> dummyCurriculumList = getDummyCurriculumValidList();
        when(curriculumRepository.findAllByIsValidIsTrue()).thenReturn(dummyCurriculumList);

        List<Curriculum> curriculumList = curriculumService.findAllCurriculumValidated();

        assertThat(curriculumList).isEqualTo(dummyCurriculumList);
    }


    @Test
    public void testFindAllCurriculumsWithValidCurriculum_withEmptyList() {
        when(curriculumRepository.findAllByIsValidIsTrue()).thenReturn(Collections.emptyList());

        List<Curriculum> curriculumList = curriculumService.findAllCurriculumValidated();

        assertThat(curriculumList).isEmpty();
    }

    @Test
    public void testFindAllByStudent_withValidStudent() {
        List<Curriculum> curriculumList = getDummyCurriculumValidList();
        Student student = getDummyStudent();
        when(curriculumRepository.findAllByStudent(any())).thenReturn(curriculumList);

        List<Curriculum> actualList = curriculumService.findAllByStudent(student);

        assertThat(actualList).isEqualTo(curriculumList);;
    }

    @Test
    public void testFindAllByStudent_withNullStudent() {
        assertThrows(IllegalArgumentException.class,
                () -> curriculumService.findAllByStudent(null));
    }

    @Test
    public void testCurriculumsToStudentCurriculumsDTO_withValidEntries() {
        Student student = getDummyStudent();
        List<Curriculum> curriculumList = getDummyCurriculumList();

        StudentCurriculumsDTO actual = curriculumService.allCurriculumsByStudentAsStudentCurriculumsDTO(student);
        assertThat(actual).isNotNull();
    }

    @Test
    public void testValidate() throws Exception {
        Curriculum curriculum = getDummyCurriculum();
        curriculum.setId(1L);

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(curriculum));

        assertThat(curriculumService.validate(curriculum.getId(), true)).isTrue();
    }

    @Test
    void testValidate_whenCvNonExistent() {
        Curriculum curriculum = getDummyCurriculum();
        curriculum.setId(1L);

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IdDoesNotExistException.class,
                () -> curriculumService.validate(curriculum.getId(), true));
    }

    @Test
    void testValidate_whenCurriculumAlreadyTreated() {
        Curriculum curriculum = getDummyCurriculum();
        curriculum.setId(1L);
        curriculum.setIsValid(true);
        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(curriculum));
        assertThrows(CurriculumAlreadyTreatedException.class, () ->
                curriculumService.validate(curriculum.getId(), true));

    }

    @Test
    void testValidate_withIdCurriculumNull() {
        assertThrows(IllegalArgumentException.class, () ->
                curriculumService.validate(null, true));
    }

    @Test
    void testReject() throws Exception {
        Curriculum dummyCurriculum = getDummyCurriculum();
        dummyCurriculum.setId(1L);

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(dummyCurriculum));

        assertThat(curriculumService.validate(dummyCurriculum.getId(), false)).isTrue();
    }

    @Test
    void testReject_whenCvNonExistent() {
        Curriculum dummyCurriculum = getDummyCurriculum();
        dummyCurriculum.setId(1L);

        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IdDoesNotExistException.class, () ->
                curriculumService.validate(dummyCurriculum.getId(), false));
    }

    @Test
    void testReject_whenCurriculumAlreadyTreated() {
        Curriculum dummyCurriculum = getDummyCurriculum();
        dummyCurriculum.setId(1L);
        dummyCurriculum.setIsValid(true);
        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(dummyCurriculum));

        assertThrows(CurriculumAlreadyTreatedException.class, () ->
                curriculumService.validate(dummyCurriculum.getId(), false));
    }

    @Test
    void testReject_withIdCurriculumNull() {
        assertThrows(IllegalArgumentException.class, () ->
                curriculumService.validate(null, false));
    }

    @Test
    void testFindOneById() throws Exception {
        Curriculum dummyCurriculum = getDummyCurriculum();
        dummyCurriculum.setId(1L);
        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(dummyCurriculum));

        Curriculum actualCurriculum = curriculumService.findOneById(dummyCurriculum.getId());

        assertThat(actualCurriculum).isEqualTo(dummyCurriculum);
    }

    @Test
    void testFindOneById_withIdNull() {
        assertThrows(IllegalArgumentException.class, () ->
                curriculumService.findOneById(null));
    }

    @Test
    void testFindOneById_withCurriculumNonExistent() {
        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IdDoesNotExistException.class, () ->
                curriculumService.findOneById(34L));
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

    private List<OfferApplication> getDummyOfferAppList() {
        List<OfferApplication> dummyOfferApplicationList = new ArrayList<>();
        OfferApplication dummyOfferApplication = new OfferApplication();
        dummyOfferApplication.setOffer(getDummyOffer());
        dummyOfferApplication.setCurriculum(getDummyCurriculumOffer());
        dummyOfferApplication.setId(1L);
        dummyOfferApplicationList.add(dummyOfferApplication);

        dummyOfferApplication.setId(2L);
        dummyOfferApplicationList.add(dummyOfferApplication);

        dummyOfferApplication.setId(3L);
        dummyOfferApplicationList.add(dummyOfferApplication);

        return dummyOfferApplicationList;
    }

    private List<CurriculumDTO> getDummyCurriculumDTOList() {
        List<CurriculumDTO> curriculumDTOList = new ArrayList<>();
        CurriculumDTO curriculumDTO1 = new CurriculumDTO();
        curriculumDTO1.setFirstName("Summer");
        curriculumDTO1.setLastName("Winter");
        curriculumDTO1.setFileName("SW_CV");
        curriculumDTO1.setFile(new byte[65 * 1024]);
        curriculumDTOList.add(curriculumDTO1);

        CurriculumDTO curriculumDTO2 = new CurriculumDTO();
        curriculumDTO2.setFirstName("Summer");
        curriculumDTO2.setLastName("Winter");
        curriculumDTO2.setFileName("SW_CV");
        curriculumDTO2.setFile(new byte[65 * 1024]);
        curriculumDTOList.add(curriculumDTO2);

        CurriculumDTO curriculumDTO3 = new CurriculumDTO();
        curriculumDTO3.setFirstName("Summer");
        curriculumDTO3.setLastName("Winter");
        curriculumDTO3.setFileName("SW_CV");
        curriculumDTO3.setFile(new byte[65 * 1024]);
        curriculumDTOList.add(curriculumDTO3);

        return curriculumDTOList;
    }

    private OfferDTO getDummyOfferDto() {
        OfferDTO dummyOfferDTO = new OfferDTO();
        dummyOfferDTO.setCreator_email("thisemail@email.com");
        dummyOfferDTO.setSalary(18.0d);
        dummyOfferDTO.setDescription("Une description");
        dummyOfferDTO.setAddress("Addresse du cégep");
        dummyOfferDTO.setTitle("Offer title");
        dummyOfferDTO.setDepartment("Department name");
        return dummyOfferDTO;
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
