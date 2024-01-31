package usePostgres.controller;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.util.List;

import usePostgres.models.Student;
import usePostgres.repositories.FacultyRepository;
import usePostgres.repositories.RecDataStudent;
import usePostgres.repositories.RecRequestStudent;
import usePostgres.repositories.StudentRepository;

import static usePostgres.controller.ServiceTesting.httpHeaders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private FacultyRepository facultyRepo;

    @Autowired
    private StudentRepository studentRepo;

    private String baseUrl;

    private final ParameterizedTypeReference parTypeStudentReference = new ParameterizedTypeReference<List<Student>>() {};

    private final HttpEntity httpEntity = new HttpEntity<>(httpHeaders());

    private String getUrl(String path, Long id) {
        return id != null
                ? String.format("%s/%s/%d", baseUrl, path, id)
                : String.format("%s/%s",  baseUrl, path);
    }

    @BeforeEach
    public void setUp() {
        baseUrl = String.format("http://localhost:%d/student", port);
    }

    @Test
    public void studentsAgeBetween() {

        final int start = 17, end = 20;
        var url = getUrl("age", null) + '/' + start + '/' + end;;

        ResponseEntity<List<Student>> lsStudent
                = template.exchange(url,
                HttpMethod.GET, httpEntity, parTypeStudentReference);

        assertThat(lsStudent).isNotNull();
        assertThat(lsStudent.getBody().size()).isGreaterThan(0);
    }

    @Test
    public void allStudentInFaculty() {
        var facultyId = 101L;
        var url = getUrl("all/ext", facultyId);

        ResponseEntity<List<RecDataStudent>> lsStudent = template.exchange(url,
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<RecDataStudent>>() {});

        assertThat(lsStudent).isNotNull();
        assertThat(lsStudent.getBody().size()).isGreaterThan(0);
    }

    @Test
    public void allStudentInFaculty2 () {
        var facultyName = "Gryffindor";
        var url = getUrl("all", null) + '/' + facultyName;

        ResponseEntity<List<Student>> lsStudent = template.exchange(url,
                HttpMethod.GET, httpEntity, parTypeStudentReference);

        assertThat(lsStudent).isNotNull();
        assertThat(lsStudent.getBody().size()).isGreaterThan(0);
    }

    @Test
    public void read() {
        Long studentId = 101L;
        //String url = getUrl("read", studentId);

        var url = "http://localhost:" + port + "/student/"+101L;

        // http://localhost:46459/student/read/101

        Student student = template.getForObject(url, Student.class);

        assertThat(student).isNotNull();
    }

    @Test
    public void RecRequestStudent_methosPost() {
        var url = getUrl("add", null);

        RecRequestStudent item =
                new RecRequestStudent(-1L, 101L, 18, "Student forTest");

        var studentFromPost = template.postForObject(url, item, Student.class);

        assertThat(studentFromPost).isNotNull();
        assertThat(studentFromPost.getName()).isEqualTo("Student forTest");
    }

    @Test
    public void delete() {
        RecRequestStudent student =
                new RecRequestStudent(-1L, 102L, 20, "Student forTest");
        var urlPost = getUrl("add", null);
        var resPost = template.postForObject(urlPost, student, Student.class);

        Long id = resPost.getId();
        var urlDelete = getUrl("delete", id);
        ResponseEntity<Student> resDel = template.exchange(urlDelete,
                HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<Student>(){} );

        assertThat(resDel).isNotNull();
        assertThat(resDel.getBody().getName()).isEqualTo("Student forTest");

    }

    @Test
    public void update() {
        var urlPost = getUrl("add", null);
        var student =
                new RecRequestStudent(-1L, 102L, 20, "Student forTest");
        var resPost = template.postForObject(urlPost, student, Student.class);

        var urlPut = getUrl("update", null);
        var recRequestStudent = new RecRequestStudent(resPost.getId(), 102L, 20, "Student update");
        template.put(urlPut, recRequestStudent);

        var studentUpdated = studentRepo.findById(resPost.getId());
        assertThat(studentUpdated.orElseThrow().getName())
                .isEqualTo("Student update");
    }

    @Test
    public void age() {

        var age = 18L;
        var url = getUrl("age", age);

        ResponseEntity<List<Student>> lsStudent = template.exchange(url,
                HttpMethod.GET, httpEntity, parTypeStudentReference);

        assertThat(lsStudent).isNotNull();
    }


}
