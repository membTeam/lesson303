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

import java.io.File;
import java.util.Scanner;

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

        try{
            studentRepo.loadDataStudent();

            System.out.println("Загружены начальные данные");

        } catch (Exception ex) {
            throw  new ErrBadRequestException("Ошибка загрузки исходных данных :\n" + ex.getMessage());
        }
    }
}
