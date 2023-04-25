import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;
import static org.junit.Assert.assertEquals;


public class IntegrationTest {

    Validator<Student> studentValidator = new StudentValidator();
    Validator<Tema> temaValidator = new TemaValidator();
    Validator<Nota> notaValidator = new NotaValidator();

    StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
    TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
    NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

    Service service = new Service(fileRepository1, fileRepository2, fileRepository3);


    @Test
    public void testAddStudentWithEmptyID() {
        assertEquals(1, service.saveStudent("","Mihai",932));
    }

    @Test
    public void TestAddAssignmentNullGroup(){
        assertEquals(1,service.saveTema("","tema random",9,8));
    }

    @Test
    public void TestAddGradeNonExistingStudent(){
        assertEquals(1,service.saveNota("1","11",9,1,"good job"));
        assertEquals(-1,service.saveNota("333","333",3,1,"wrong"));
    }

    @Test
    public void TestIntegration(){
        assertEquals(0, service.saveStudent("321","Mihai",932));
        assertEquals(0,service.saveTema("931","tema random",9,8));
        assertEquals(1,service.saveNota("321","931",9,1,"good job"));
        assertEquals(-1,service.saveNota("333","333",3,1,"wrong"));

    }
}
