package usePostgres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import usePostgres.exception.ErrBadRequestException;
import usePostgres.models.Faculty;
import usePostgres.models.Student;
import usePostgres.repositories.AvatarRepository;
import usePostgres.repositories.FacultyRepository;
import usePostgres.repositories.StudentRepository;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

@Configuration
public class LoadData implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private FacultyRepository facultyRepo;

    @Autowired
    private AvatarRepository avatarRepo;

    @Override
    public void run(String... args) {
        try(Scanner scanner = new Scanner(new File("loadData.csv"))) {

            avatarRepo.deleteAll();
            studentRepo.deleteAll();
            var id = studentRepo.getMaxID().orElse(1L);

            while (scanner.hasNextLine()) {
                var arr = scanner.nextLine().split(",");

                Long idFaculty = Long.parseLong(arr[0].trim());
                Faculty faculty = facultyRepo.getReferenceById(idFaculty);

                var student = new Student();

                student.setId(id++);
                student.setFaculty(faculty);
                student.setAge(Integer.parseInt(arr[1].trim()));
                student.setName(arr[2].trim());

                studentRepo.save(student);
            }

            System.out.println("Загружены начальные данные");

        } catch (Exception ex) {
            throw  new ErrBadRequestException("Ошибка загрузки исходных данных :\n" + ex.getMessage());
        }
    }
}
