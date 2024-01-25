package usePostgres.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import usePostgres.models.Faculty;
import usePostgres.models.Student;
import usePostgres.repositories.FacultyRepository;
import usePostgres.repositories.RecRequestStudent;
import usePostgres.service.FacultyService;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class FacultyControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepo;
    @MockBean
    private FacultyService facultyServ;

    public static Faculty createFaculty() {

        var faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("facultyTesting");
        faculty.setColor("color");

        return faculty;
    }
    public static JSONObject creatJSONobjRecRequest(Faculty faculty) throws Exception {
        JSONObject facultyObj = new JSONObject();
        facultyObj.put("id", 1L);
        facultyObj.put("name", faculty.getName());
        facultyObj.put("color", faculty.getColor());

        return facultyObj;
    }

    @Test
    public void add() throws Exception {
        var faculty = createFaculty();
        var facultyJsonObj = creatJSONobjRecRequest(faculty);

        when(facultyServ.add(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty/add")
                        .content(facultyJsonObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()));
    }

    @Test
    public void read() throws Exception {
        var faculty = createFaculty();

        when(facultyServ.read(any(Long.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/read/" + faculty.getId() )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()));
    }

    @Test
    public void update() throws Exception {
        var faculty = createFaculty();
        var studentObj = creatJSONobjRecRequest(faculty);
        when(facultyServ.update(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/update")
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()));
    }

    @Test
    public void delete() throws Exception {
        var faculty = createFaculty();

        when(facultyServ.delete(any(Long.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/delete/"+ faculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()));
    }


}
