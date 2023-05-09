import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IncrementalTesting {

    private Service mockAllService;
    @Mock
    private StudentXMLRepository mockStudentRepo;
    @Mock
    private TemaXMLRepository mockTemaRepo;
    @Mock
    private NotaXMLRepository mockNotaRepo;

    @Before
    public void initializeData() {
        mockStudentRepo = mock(StudentXMLRepository.class);
        mockTemaRepo = mock(TemaXMLRepository.class);
        mockNotaRepo = mock(NotaXMLRepository.class);
        mockAllService = new Service(mockStudentRepo, mockTemaRepo, mockNotaRepo);
    }

    @Test
    public void saveStudentValidForIntegration() {

        when(mockStudentRepo.save(any())).thenReturn(new Student("1", "Ion", 932));
        String validId = "1";
        String validName = "Ion";
        int validGroup = 932;
        assert(mockAllService.saveStudent(validId, validName, validGroup) == 1);
    }

    @Test
    public void saveAssignmentIntegrateAddStudent() {
        // arrange
        StudentXMLRepository actualStudentRepo = new StudentXMLRepository(new StudentValidator(), "student_test.xml");

        if (actualStudentRepo.findOne("1") != null) {
            actualStudentRepo.delete("1");
        }

        Service mockAllButStudentService = new Service(actualStudentRepo, mockTemaRepo, mockNotaRepo);
        String validId = "1";
        String validName = "Ion";
        int validGroup = 932;
        String validIdTema = "1";
        String validDescription = "Lab1";
        int validDeadline = 6;
        int validStartWeek = 4;

        when(mockTemaRepo.save(any())).thenReturn(new Tema("0", "Lab1", 6, 4));

        // act
        int saveStudentResult = mockAllButStudentService.saveStudent(validId, validName, validGroup);
        int saveAssignmentResult = mockAllButStudentService.saveTema(validId, validDescription, validDeadline, validStartWeek);

        // assert
        assert (saveStudentResult == 1);
        assert (saveAssignmentResult == 1);
    }

    @Test
    public void saveGradeIntegrateAddAsignmentAndAddGrade() {
        // arrange
        StudentXMLRepository actualStudentRepo = new StudentXMLRepository(new StudentValidator(), "student_test.xml");
        TemaXMLRepository actualTemaRepo = new TemaXMLRepository(new TemaValidator(), "tema_test.xml");

        if (actualStudentRepo.findOne("1") != null) {
            actualStudentRepo.delete("1");
        }

        if (actualTemaRepo.findOne("1") != null) {
            actualTemaRepo.delete("1");
        }

        Service mockOnlyGradeService = new Service(actualStudentRepo, actualTemaRepo, mockNotaRepo);

        String validId = "1";
        String validName = "Ion";
        int validGroup = 932;
        String validDescription = "Lab1";
        int validDeadline = 6;
        int validStartWeek = 4;
        Pair<String, String> validGradeId = new Pair<String, String>("1", "1");
        Double validGrade = 10.00;
        int validSaptamanaPredare = 8;
        String validFeedback = "bravo";

        when(mockNotaRepo.save(any())).thenReturn(new Nota(new Pair<String, String>("1", "1"), 10, 8, "bravo"));

        // act
        int saveStudentResult = mockOnlyGradeService.saveStudent(validId, validName, validGroup);
        int saveAssignmentResult = mockOnlyGradeService.saveTema(validId, validDescription, validDeadline, validStartWeek);
        int saveGradeResult = mockOnlyGradeService.saveNota(validGradeId.getObject1(), validGradeId.getObject2(), validGrade, validSaptamanaPredare, validFeedback);

        // assert
        assert (saveStudentResult == 1);
        assert (saveAssignmentResult == 1);
        assert (saveGradeResult == 1);
    }
}