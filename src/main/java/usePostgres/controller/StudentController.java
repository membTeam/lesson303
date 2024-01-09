package usePostgres.controller;

import org.springframework.web.bind.annotation.*;
import usePostgres.models.Student;
import usePostgres.service.StudentService;

@RestController
@RequestMapping("student")
public class StudentController {

    private StudentService studentServ;

    public StudentController(StudentService studentServ) {
        this.studentServ = studentServ;
    }

    @GetMapping("read/{id}")
    public Student read(@PathVariable Long id) {
        return studentServ.read(id);
    }

    @PostMapping("add")
    public Student add(@RequestBody Student item) {
        return studentServ.add(item);
    }

    @DeleteMapping("delete/{id}")
    public Student delete(@PathVariable Long id) {
        return studentServ.delete(id);
    }


    @PutMapping("update")
    public Student delete(@RequestBody Student item) {
        return studentServ.update(item);
    }



}
