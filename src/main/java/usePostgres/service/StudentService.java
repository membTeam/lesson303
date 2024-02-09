package usePostgres.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import usePostgres.exception.ErrBadRequestException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import usePostgres.models.Student;
import usePostgres.repositories.*;

import static usePostgres.exception.RunErrBadRequestException.runException;

@Service
public class StudentService {

    private Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepo;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepo, FacultyRepository facultyRepo,
                          FacultyRepository facultyRepository) {
        this.studentRepo = studentRepo;
        this.facultyRepository = facultyRepository;
    }

    private void checkData(RecRequestStudent item) {
        String strErr = "";

        if (item.age() == 0 || item.age() < 15 || item.age() > 25) {
            strErr = "Возраст д/быть больше 15 и меньше 25";
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


    public Collection<Student> getLastFiveStudent(int numberLastItem) {
        logger.info("Выборка 5 последних студентов");
        return studentRepo.getLastFiveStudent(numberLastItem);
    }

    public Integer getAvgStudent() {
        logger.info("get avgData");
        return (int) studentRepo.getAvgStudent();
    }

    public Integer getAllAmountStudent() {
        logger.info("get allAmount");
        return studentRepo.getAllAmountStudent();
    }

    public List<Student> studentsAgeBetween(Integer start, Integer end) {
        logger.info("Запрос на средний возраст");
        return studentRepo.findByAgeBetween(start, end);
    }

    public List<RecDataStudent> allStudentInFaculty(Long id) {
        logger.info("Чтение студентов на факультете facultyId:" + id);
        return studentRepo.findStudentInFaculty(id)
                .stream().map(obj-> new RecDataStudent(obj))
                .collect(Collectors.toList());
    }

    public List<Student> allStudentInFaculty(String faculty) {
        logger.info("Чтение студентов на факультете name:" + faculty);
        return studentRepo.findStudentInFaculty(faculty);
    }

    public Iterable<Student> age(Integer age) {
        return studentRepo.findByAge(age);
    }

    public Student add(RecRequestStudent item) {

        checkData(item);

        if (item.id()>=0 && studentRepo.existDataForStudentName(item.name())) {
            runException("Повторный ввод данных");
        }

        var maxId = item.id() >= 0 ? studentRepo.getMaxID() : studentRepo.getMaxIDForTesting();

        var student = new Student();
        var faculty = facultyRepository.getReferenceById(item.facultyId());

        student.setId(++maxId);
        student.setAge(item.age());
        student.setName(item.name());
        student.setFaculty(faculty);

        logger.info(String.format("Добавление студента id:%d name:%s", student.getId(), student.getName()));
        return studentRepo.save(student);
    }

    public Student read(Long id) {
        logger.info("Чтение данные студента по id" + id);
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

        logger.info(String.format("Обновление id:%d name:%s", student.getId(), student.getName()));

        return studentRepo.save(student);
    }

    public Student delete(Long id) {
        var item = studentRepo.findById(id)
                .orElseThrow(()-> {throw new ErrBadRequestException("Нет данных по id");});

        logger.info(String.format("Удаление id:%d name:%s", item.getId(), item.getName()));

        studentRepo.deleteById(id);

        return item;
    }

}
