
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

public class AddAssignmentTest {

    Validator<Student> studentValidator = new StudentValidator();
    Validator<Tema> temaValidator = new TemaValidator();
    Validator<Nota> notaValidator = new NotaValidator();

    StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
    TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
    NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

    Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

    @Test
    public void TestAddAssignment(){

        //test case 1 id >0 and a number
        assertEquals(0,service.saveTema("999","tema random",9,8));
        assertEquals(1,service.saveTema("-999","tema random",9,8));
        assertEquals(1,service.saveTema("a","tema random",9,8));


    }
    @Test
    public void TestAddAssignmentDescription(){

        //test case 2 description must not be empty
        assertEquals(1,service.saveTema("999","",9,8));
        assertEquals(0,service.saveTema("999"," ",9,8));
        assertEquals(0,service.saveTema("999","t",9,8));
    }

    @Test
    public void TestAddAssignmentDeadline(){
        // deadline > startline
        assertEquals(1,service.saveTema("999","tema random",7,8));
        assertEquals(0,service.saveTema("999","tema random",9,8));
        assertEquals(0,service.saveTema("999","tema random",7,7));

    }

}
