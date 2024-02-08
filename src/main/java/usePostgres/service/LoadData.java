package usePostgres.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import usePostgres.exception.ErrBadRequestException;
import usePostgres.repositories.AvatarRepository;
import usePostgres.repositories.FacultyRepository;
import usePostgres.repositories.StudentRepository;

@Configuration
public class LoadData implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(LoadData.class);

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
            logger.info("Загружены начальные данные");
        } catch (Exception ex) {
            logger.error("Ошибка загрузки исхДанных: " + ex.getMessage() );
            throw  new ErrBadRequestException("Ошибка загрузки исходных данных :\n" + ex.getMessage());
        }
    }
}
