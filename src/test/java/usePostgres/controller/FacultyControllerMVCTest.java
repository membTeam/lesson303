package usePostgres.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static usePostgres.controller.ServiceTesting.creatJSONobjRecRequest;

import usePostgres.models.Faculty;
import usePostgres.repositories.FacultyRepository;
import static usePostgres.controller.ServiceTesting.createFaculty;

@SpringBootTest
@AutoConfigureMockMvc
public class FacultyControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepo;

    @Test
    public void add() throws Exception {
        var faculty = createFaculty();
        faculty.setId(-1L);

        var facultyRes = createFaculty();

        var facultyJsonObj = creatJSONobjRecRequest(faculty);
        facultyJsonObj.put("id", -1L);
        boolean existFaculty = true;

        when(facultyRepo.existDataForFacultyName(any(String.class))).thenReturn(existFaculty);
        when(facultyRepo.getMaxIdForTesting()).thenReturn(0L);
        when(facultyRepo.save(any(Faculty.class))).thenReturn(facultyRes);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty/add")
                        .content(facultyJsonObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyRes.getId()))
                .andExpect(jsonPath("$.name").value(facultyRes.getName()));
    }

    @Test
    public void read() throws Exception {
        var faculty = createFaculty();

        when(facultyRepo.findById(any(Long.class))).thenReturn(Optional.of(faculty));

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

        when(facultyRepo.save(any(Faculty.class))).thenReturn(faculty);

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

        doNothing().when(facultyRepo).deleteById(any(Long.class));
        when(facultyRepo.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/delete/"+ faculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()));
    }


}
