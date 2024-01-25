package usePostgres.controller;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import usePostgres.models.DataStudentImpl;
import usePostgres.models.Student;
import usePostgres.repositories.FacultyRepository;
import usePostgres.repositories.StudentRepository;
import usePostgres.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
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
    private FacultyRepository facultyRepo;

    @InjectMocks
    private StudentService studentServ;

    @Test
    public void studentsAgeBetween() throws Exception {
        final int start = 17, end = 20;
        List<Student> lsStudent = List.of(createStudent());

        when(studentRepo.findByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(lsStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/" + start + '/'+end )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void allExt() throws Exception {
        var facultyId = 101L;
        var dataStudentImpl = new DataStudentImpl(1L, "facultyName", "nameStudent", 18);
        var lsResFromRepo =  List.of(dataStudentImpl);

        when(studentRepo.findStudentInFaculty(any(Long.class))).thenReturn(lsResFromRepo);
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

        when(studentRepo.findStudentInFaculty(any(String.class))).thenReturn(lsStudent);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/all/" + strFaculty )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void read() throws Exception {
        var student = createStudent();

        when(studentRepo.findById(any(Long.class))).thenReturn( Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/read/" + student.getId() )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()));
    }

    @Test
    public void add() throws Exception {
        var faculty = createFaculty();
        var student = createStudent();
        student.setId(-1L);
        var studentRes = createStudent();

        var studentObj = creatJSONobjRecRequest(student);

        when(studentRepo.existDataForStudentName(any(String.class))).thenReturn(false);
        when(studentRepo.getMaxIDForTesting()).thenReturn(0L);
        when(facultyRepo.getReferenceById(any(Long.class))).thenReturn(faculty);
        when(studentRepo.save(any(Student.class))).thenReturn(studentRes);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/add")
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentRes.getId()))
                .andExpect(jsonPath("$.name").value(studentRes.getName()));
    }

    @Test
    public void delete() throws Exception {
        var student = createStudent();

        when(studentRepo.findById(any(Long.class))).thenReturn( Optional.of(student));
        doNothing().when(studentRepo).deleteById(any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/delete/"+ student.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()));
    }
    @Test
    public void update() throws Exception {
        var faculty = createFaculty();
        var student = createStudent();
        student.setId(-1L);
        student.setFaculty(faculty);

        var studentRes = createStudent();
        var studentObj = creatJSONobjRecRequest(student);

        when(studentRepo.findById(any(Long.class))).thenReturn( Optional.of(student));
        when(facultyRepo.getReferenceById(any(Long.class))).thenReturn(faculty);
        when(studentRepo.save(any(Student.class))).thenReturn(studentRes);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/update")
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentRes.getId()))
                .andExpect(jsonPath("$.name").value(studentRes.getName()));
    }
    @Test
    public void age() throws Exception {

        var student = createStudent();
        List<Student> lsStudent = List.of(student);

        when(studentRepo.findByAge(any(Integer.class))).thenReturn(lsStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/" + student.getAge() )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

}
