import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import java.util.Objects;

public class TestAddStudent {

    Validator<Student> studentValidator = new StudentValidator();
    Validator<Tema> temaValidator = new TemaValidator();
    Validator<Nota> notaValidator = new NotaValidator();

    StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
    TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
    NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

    Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

    @Test
    void testAddStudentSuccess() {

        Assertions.assertEquals(0, service.saveStudent("999","nume",932));
    }

    @Test
    void testAddStudentExisting() {
        service.saveStudent("999","nume",932);
        Assertions.assertEquals(1, service.saveStudent("999","nume",932));
    }

    @Test
    void checkExistingStudent() {
        service.saveStudent("999","nume",932);
        Student s1 = new Student("999","nume",932);
        Student s2 = new Student("999","nume",932);
        Assertions.assertTrue(Objects.equals(s1.getID(), s2.getID()));
    }
}
