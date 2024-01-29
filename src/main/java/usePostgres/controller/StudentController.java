package usePostgres.controller;

import org.springframework.web.bind.annotation.*;
import usePostgres.models.Student;
import usePostgres.repositories.RecDataStudent;
import usePostgres.repositories.RecRequestStudent;
import usePostgres.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private StudentService studentServ;

    public StudentController(StudentService studentServ) {
        this.studentServ = studentServ;
    }

    @GetMapping("amount-student")
    public Integer amountStudent() {
        return null;
    }

    @GetMapping("age/{start}/{end}")
    public List<Student> studentsAgeBetween(@PathVariable Integer start, @PathVariable Integer end) {
        return studentServ.studentsAgeBetween(start, end);
    }

    @GetMapping("all/ext/{id}")
    public List<RecDataStudent> allStudentInFaculty(@PathVariable Long id) {
        return studentServ.allStudentInFaculty(id);
    }

    @GetMapping("all/{faculty}")
    public List<Student> allStudentInFaculty(@PathVariable String faculty) {
        return studentServ.allStudentInFaculty(faculty);
    }

    @GetMapping("read/{id}")
    public Student read(@PathVariable Long id) {
        return studentServ.read(id);
    }

    @PostMapping("add")
    public Student add(@RequestBody RecRequestStudent item) {
        return studentServ.add(item);
    }

    @DeleteMapping("delete/{id}")
    public Student delete(@PathVariable Long id) {
        return studentServ.delete(id);
    }

    @PutMapping("update")
    public Student delete(@RequestBody RecRequestStudent item) {
        return studentServ.update(item);
    }

    @GetMapping("age/{age}")
    public Iterable<Student> age(@PathVariable Integer age) {
        return studentServ.age(age);
    }

}
