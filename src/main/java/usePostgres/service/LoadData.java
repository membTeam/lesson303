package usePostgres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import usePostgres.exception.ErrBadRequestException;
import usePostgres.models.Student;
import usePostgres.repositories.StudentRepository;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

@Configuration
public class LoadData implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepo;

    @Override
    public void run(String... args) throws Exception {
        try(Scanner scanner = new Scanner(new File("loadData.csv"))) {

            var id = 1L;

            studentRepo.deleteAll();
            while (scanner.hasNextLine()) {
                var arr = scanner.nextLine().split(",");

                var student = new Student();
                student.setId(id++);
                student.setFacultyId(Long.parseLong(arr[0].trim()));
                student.setAge(Integer.parseInt(arr[1].trim()));
                student.setName(arr[2].trim());

                studentRepo.save(student);
            }



            System.out.println("Загружены начальные данные");
        } catch (IOException ex) {
            throw  new ErrBadRequestException("Ошибка загрузки исходных данных :\n" + ex.getMessage());
        }
    }
}
