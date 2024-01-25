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
import usePostgres.models.Student;
import usePostgres.repositories.RecDataStudent;
import usePostgres.repositories.RecRequestStudent;
import usePostgres.repositories.StudentRepository;
import usePostgres.service.StudentService;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static usePostgres.controller.ServiceTesting.*;
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTestMVC {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepo;
    @MockBean
    private StudentService studentServ;

    @Test
    public void studentsAgeBetween() throws Exception {
        final int start = 17, end = 20;
        List<Student> lsStudent = List.of(createStudent());

        when(studentServ.studentsAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(lsStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/" + start + '/'+end )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void allExt() throws Exception {
        var facultyId = 101L;
        var recDataStudent = new RecDataStudent(1L,
                "facultyName",
                "nameStudent", 18);
        List<RecDataStudent> lsStudent = List.of(recDataStudent);

        when(studentServ.allStudentInFaculty(any(Long.class))).thenReturn(lsStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/all/ext/" + facultyId )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void all() throws Exception {
        var strFaculty = "Gryffindor";
        List<Student> lsStudent = List.of(createStudent());

        when(studentServ.allStudentInFaculty(any(String.class))).thenReturn(lsStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/all/" + strFaculty )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void read() throws Exception {
        var student = createStudent();

        when(studentServ.read(any(Long.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/read/" + student.getId() )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()));
    }

    @Test
    public void add() throws Exception {
        var student = createStudent();
        var studentObj = creatJSONobjRecRequest(student);

        when(studentServ.add(any(RecRequestStudent.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/add")
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()));
    }

    @Test
    public void delete() throws Exception {
        var student = createStudent();

        when(studentServ.delete(any(Long.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/delete/"+ student.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()));
    }
    @Test
    public void update() throws Exception {
        var student = createStudent();
        var studentObj = creatJSONobjRecRequest(student);
        when(studentServ.update(any(RecRequestStudent.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/update")
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()));
    }
    @Test
    public void age() throws Exception {

        var student = createStudent();
        List<Student> lsStudent = List.of(student);

        when(studentServ.age(any(Integer.class))).thenReturn(lsStudent);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/" + student.getAge() )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

}
