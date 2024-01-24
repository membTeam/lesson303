package usePostgres.service;

import org.springframework.stereotype.Service;
import usePostgres.exception.ErrBadRequestException;
import java.util.List;
import java.util.stream.Collectors;

import usePostgres.models.Student;
import usePostgres.repositories.*;

import static usePostgres.exception.RunErrBadRequestException.runException;

@Service
public class StudentService {

    private final StudentRepository studentRepo;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepo, FacultyRepository facultyRepo,
                          FacultyRepository facultyRepository) {
        this.studentRepo = studentRepo;
        this.facultyRepository = facultyRepository;
    }

    private void checkData(RecRequestStudent item) {
        String strErr = "";

        if (item.age() == 0 || item.age() < 17 || item.age() > 25) {
            strErr = "Возраст д/быть больше 17 и меньше 25";
        }

        if (item.name() == null || item.name().isBlank()) {
            var s = "Нет данных по имени";
            strErr = strErr.isBlank() ? s : " " + s;
        }

        if (item.facultyId() == 0) {
            strErr  = strErr + " Нет данных по факультету";
        }

        if (!strErr.isBlank()) {
            runException(strErr);
        }
    }

    public List<Student> studentsAgeBetween(Integer start, Integer end) {
        return studentRepo.findByAgeBetween(start, end);
    }

    public List<RecDataStudent> allStudentInFaculty(Long id) {

        return studentRepo.findStudentInFaculty(id)
                .stream().map(obj-> new RecDataStudent(obj))
                .collect(Collectors.toList());
    }

    public List<Student> allStudentInFaculty(String faculty) {
        return studentRepo.findStudentInFaculty(faculty);
    }

    public Iterable<Student> age(Integer age) {
        return studentRepo.findByAge(age);
    }

    public Student add(RecRequestStudent item) {

        checkData(item);

        if (studentRepo.existDataForStudentName(item.name())) {
            runException("Повторный ввод данных");
        }

        var maxId = studentRepo.getMaxID();

        var student = new Student();
        var faculty = facultyRepository.getReferenceById(item.facultyId());

        student.setId(++maxId);
        student.setAge(item.age());
        student.setName(item.name());
        student.setFaculty(faculty);

        return studentRepo.save(student);
    }

    public Student read(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(()->{throw new ErrBadRequestException("Нет данных по id");});
    }

    public Student update(RecRequestStudent item) {

        checkData(item);

        var student = studentRepo.findById(item.id())
                .orElseThrow(()-> {throw new ErrBadRequestException("Нет данных по студенту");});

        student.setName(item.name());
        student.setAge(item.age());
        if (!student.getFaculty().getId().equals(item.facultyId())) {
            var faculty = facultyRepository.getReferenceById(item.facultyId());
            student.setFaculty(faculty);
        }

        return studentRepo.save(student);
    }

    public Student delete(Long id) {
        var item = studentRepo.findById(id)
                .orElseThrow(()-> {throw new ErrBadRequestException("Нет данных по id");});

        studentRepo.deleteById(id);

        return item;
    }

}
