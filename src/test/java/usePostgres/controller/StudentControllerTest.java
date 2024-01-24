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
import usePostgres.models.Student;
import usePostgres.repositories.RecDataStudent;

import java.util.List;
import static usePostgres.controller.HTTPHeadersObj.httpHeaders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate template;

    private String baseUrl;

    private final ParameterizedTypeReference parTypeStudentReference = new ParameterizedTypeReference<List<Student>>() {};

    private final HttpEntity httpEntity = new HttpEntity<>(httpHeaders());

    @BeforeEach
    public void setUp() {
        baseUrl = String.format("http://localhost:%d/student", port);
    }

    @Test
    public void studentsAgeBetween() {

        final int start = 17, end = 20;
        var url = String.format("%s/age/%d/%d", baseUrl, start, end);

        ResponseEntity<List<Student>> lsStudent
                = template.exchange(url,
                HttpMethod.GET, httpEntity, parTypeStudentReference);

        assertThat(lsStudent).isNotNull();
        assertThat(lsStudent.getBody().size()).isGreaterThan(0);
    }

    @Test
    public void allStudentInFaculty() {
        var facultyId = 1L;
        var url = String.format("%s/all/ext/%d", baseUrl, facultyId);

        ResponseEntity<List<RecDataStudent>> lsStudent
                = template.exchange(url,
                HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<List<RecDataStudent>>() {});

        assertThat(lsStudent).isNotNull();
        assertThat(lsStudent.getBody().size()).isGreaterThan(0);
    }

    @Test
    public void allStudentInFaculty2 () {
        var facultyName = "Gryffindor";
        var url = String.format("%s/all/%s", baseUrl, facultyName);

        ResponseEntity<List<Student>> lsStudent = template.exchange(url,
                HttpMethod.GET, httpEntity, parTypeStudentReference);

        assertThat(lsStudent).isNotNull();
        assertThat(lsStudent.getBody().size()).isGreaterThan(0);
    }

    @Test
    public void read() {
        var studentId = 1L;
        var url = String.format("%s/read/%d", baseUrl, studentId);

        Student student = template.getForObject(url, Student.class);
        assertThat(student).isNotNull();
    }

    @Test
    public void RecRequestStudent_methosPost() {
        var student = new Student();
        student.setId(100L);
        student.setAge(18);
        student.setName("Student forTest");

        // TODO: использовать HQL for get faculty


    }

}
