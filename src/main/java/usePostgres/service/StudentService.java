package usePostgres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usePostgres.exception.ErrBadRequestException;
import usePostgres.models.Student;
import usePostgres.repositories.FacultyRepository;
import usePostgres.repositories.StudentRepository;

import java.util.List;

import static usePostgres.exception.RunErrBadRequestException.runException;

@Service
public class StudentService {

    private StudentRepository studentRepo;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepo, FacultyRepository facultyRepo,
                          FacultyRepository facultyRepository) {
        this.studentRepo = studentRepo;
        this.facultyRepository = facultyRepository;
    }

    private void checkData(Student item) {
        String strErr = "";

        if (item.getAge() == 0 || item.getAge() < 17 || item.getAge() > 25) {
            strErr = "Возраст д/быть больше 17 и меньше 25";
        }

        if (item.getName() == null || item.getName().isBlank()) {
            var s = "Нет данных по имени";
            strErr = strErr.isBlank() ? s : " " + s;
        }

        if (!studentRepo.existDataForFaculty(item.getFacultyId())) {
            var s = "Нет данных по факультету";
            strErr = strErr.isBlank() ? s : " " + s;
        }

        if (!strErr.isBlank()) {
            runException(strErr);
        }
    }

    public List<Student> allStudentInFaculty(String faculty) {
        return studentRepo.findStudentForFaculty(faculty);
    }

    public Iterable<Student> age(Integer age) {
        return studentRepo.findByAge(age);
    }

    public Student add(Student item) {

        checkData(item);

        if (studentRepo.existDataForName(item.getName())) {
            runException("Повторный ввод данных");
        }

        var getMaxId = studentRepo.getMaxID();
        item.setId(++getMaxId);

        return studentRepo.save(item);
    }

    public Student read(Long id) {
        if ( studentRepo.findById(id).isEmpty()) {
            runException("Нет данных по id " + id);
        }

        var res = studentRepo.findById(id).orElseThrow();
        String facultyName = facultyRepository
                .findById(res.getFacultyId())
                .orElseThrow().getName();
        res.setFacultyName(facultyName);

        return res;
    }

    public Student update(Student item) {

        if (item.getId() == null || item.getId() == 0 ) {
            runException("Нет данных");
        }

        checkData(item);
        return studentRepo.save(item);
    }

    public Student delete(Long id) {
        var item = studentRepo.findById(id);
        if (item.isEmpty()) {
            runException("Нет данных");
        }

        studentRepo.deleteById(id);
        return item.orElseThrow(()-> {throw new ErrBadRequestException("Нет данных по идентификатору");});
    }

}
