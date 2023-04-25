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


public class TestAddStudent {

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
    public void testAddStudentWithNullID() {
        assertEquals(1, service.saveStudent(null,"Mihai",932));
    }

    @Test
    public void testAddStudentWithEmptyName() {
        assertEquals(1, service.saveStudent(String.valueOf(13),"",932));
    }

    @Test
    public void testAddStudentWithInvalidName() {
        assertEquals(1, service.saveStudent(String.valueOf(14),null,932));
    }

    @Test
    public void testAddStudentWithGroupToSmall() {
        assertEquals(1, service.saveStudent(String.valueOf(15),"Mihai",10));
    }

    @Test
    public void testAddStudentWithGroupToBig() {
        assertEquals(1, service.saveStudent(String.valueOf(16),"Mihai",999));
    }


    @Test
    public void testAddStudentExisting() {
        //should refactor the code so that adding the same student twice throws an error
        service.saveStudent("999","nume",932);
        assertEquals(0, service.saveStudent("999","nume",932));
    }

    @Test
    public void  checkExistingStudent() {
        service.saveStudent("999","nume",932);
        Student s1 = new Student("999","nume",932);
        Student s2 = new Student("999","nume",932);
        assertEquals(s1.getID(), s2.getID());
        assertEquals(s1,s2);
    }
}
