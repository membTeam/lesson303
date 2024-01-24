package usePostgres.controller;

import org.springframework.boot.logging.java.JavaLoggingSystem;
import org.springframework.web.bind.annotation.*;
import usePostgres.models.Faculty;
import usePostgres.repositories.FacultyRepository;
import usePostgres.service.FacultyService;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("add")
    public Faculty add(@RequestBody Faculty faculty) {
        return facultyService.add(faculty);
    }

    @GetMapping("read/{id}")
    public Faculty read(@PathVariable Long id) {
        return facultyService.read(id);
    }

    @PutMapping("update")
    public Faculty update(@RequestBody Faculty faculty) {
        return facultyService.update(faculty);
    }

    @DeleteMapping("delete/{id}")
    public Faculty delete(@PathVariable Long id) {
        return facultyService.delete(id);
    }
}
