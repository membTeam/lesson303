package usePostgres.controller;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import usePostgres.models.Faculty;
import usePostgres.models.Student;

public class ServiceTesting {
    public static HttpHeaders httpHeaders() {

        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }

    public static JSONObject creatJSONobjRecRequest(Faculty faculty) throws Exception {
        JSONObject facultyObj = new JSONObject();
        facultyObj.put("id", 1L);
        facultyObj.put("name", faculty.getName());
        facultyObj.put("color", faculty.getColor());

        return facultyObj;
    }

    public static Faculty createFaculty() {

        var faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("facultyTesting");
        faculty.setColor("color");

        return faculty;
    }

    public static Student createStudent() {

        var student = new Student();
        student.setId(1L);
        student.setName("Name for testing");
        student.setAge(18);

        return student;
    }

    public static JSONObject creatJSONobjRecRequest(Student student) throws Exception {
        JSONObject studentObj = new JSONObject();
        studentObj.put("id", 1L);
        studentObj.put("facultyId", 101L);
        studentObj.put("age", student.getAge());
        studentObj.put("name", student.getName());

        return studentObj;
    }

}
