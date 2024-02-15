package usePostgres.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import usePostgres.models.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MappedOneToOneTest {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private AvatarRepository avatarRepo;


    @Test
    public void getAvatarData() {
        // использование внутренних связей -> join
        var item = studentRepo.getAvatarData(54563);

        assertTrue(item.size()>0);

        assertEquals("avatars/Tom Riddle.png", item.get(0).getFilePath());
    }

    @Test
    public void getAvatarFromStudent() {

        var item = studentRepo.getAvatarFromStudent();

        assertTrue(item.size()>0);

        assertNotNull(item.get(0)[0]);
        assertNotNull(item.get(0)[1]);

        var student = (Student) item.get(0)[0];
        assertEquals("Tom Riddle", student.getName());

    }


}
