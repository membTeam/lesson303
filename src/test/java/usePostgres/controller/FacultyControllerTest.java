package usePostgres.controller;

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
import usePostgres.models.Faculty;
import usePostgres.repositories.FacultyRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static usePostgres.service.HTTPHeaders.httpHeaders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @Autowired
    private FacultyRepository facultyRepo;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;
    private String baseUrl;
    @BeforeEach
    public void setUp() {
        baseUrl = String.format("http://localhost:%d/faculty", port);
    }
    private final HttpEntity httpEntity = new HttpEntity<>(httpHeaders());

    private String getUrl(String path, Long id) {
        return id != null
                    ? String.format("%s/%s/%d", baseUrl, path, id)
                    : String.format("%s/%s",  baseUrl, path);
    }

    public static Faculty createFaculty() {
        var faculty = new Faculty();
        faculty.setId(-1L);
        faculty.setName("facultyTesing");
        faculty.setColor("color");

        return faculty;
    }
    // -------------------------------------------------

    @Test
    public void add() {
        var url = getUrl("add", null);
        var resPost = template.postForObject(url, createFaculty(), Faculty.class);

        assertThat(resPost).isNotNull();
        assertThat(resPost.getName()).isEqualTo("facultyTesing");
    }

    @Test
    public void read() {
        var url = getUrl("read", 101L);

        Faculty faculty = template.getForObject(url, Faculty.class);
        assertThat(faculty).isNotNull();
    }

    @Test
    public void update() {
        var urlPost = getUrl("add", null);
        var resPost = template.postForObject(urlPost, createFaculty(), Faculty.class);

        resPost.setName("facultyTesting update");
        var urlPut = getUrl("update", null);
        template.put(urlPut, resPost);

        var facultyUpdate = facultyRepo.findById(resPost.getId());
        assertThat(facultyUpdate.orElseThrow().getName())
                .isEqualTo("facultyTesting update");
    }

    @Test
    public void delsete() {
        var urlPost = getUrl("add", null);
        var resPost = template.postForObject(urlPost, createFaculty(), Faculty.class);

        var urlDelete = getUrl("delete", resPost.getId());
        ResponseEntity<Faculty> resDel = template.exchange(urlDelete,
                HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<Faculty>(){} );

        assertThat(resDel.getBody()).isNotNull();
        assertThat(resDel.getBody().getName()).isEqualTo("facultyTesing");
    }

}
