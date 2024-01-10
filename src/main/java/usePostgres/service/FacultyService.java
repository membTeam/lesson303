package usePostgres.service;

import org.springframework.stereotype.Service;
import usePostgres.exception.ErrBadRequestException;
import usePostgres.models.Faculty;
import usePostgres.models.Student;
import usePostgres.repositories.FacultyRepository;

import static usePostgres.exception.RunErrBadRequestException.runException;

@Service
public class FacultyService {
    private FacultyRepository facultyRepo;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepo = facultyRepository;
    }

    private void checkData(Faculty item) {
        String strErr = "";

        if (item.getName() == null || item.getName().isBlank()) {
            var s = "Нет данных по имени";
            strErr = strErr.isBlank() ? s : " " + s;
        }

        if (item.getColor() == null || item.getColor().isBlank()) {
            var s = "Нет данных по цвету";
            strErr = strErr.isBlank() ? s : " " + s;
        }

        if (!strErr.isBlank()) {
            runException(strErr);
        }
    }

    public Faculty add(Faculty item) {

        checkData(item);

        if (facultyRepo.existDataForName(item.getName())) {
            runException("Повторный ввод данных");
        }

        var getMaxId = facultyRepo.getMaxID();
        item.setId(++getMaxId);

        return facultyRepo.save(item);
    }

    public Faculty read(Long id) {
        if ( facultyRepo.findById(id).isEmpty()) {
            runException("Нет данных по id " + id);
        }

        return facultyRepo.findById(id).orElseThrow();
    }

    public Faculty update(Faculty item) {

        if (item.getId() == null || item.getId() == 0 ) {
            runException("Нет данных");
        }

        checkData(item);
        return facultyRepo.save(item);
    }

    public Faculty delete(Long id) {
        var item = facultyRepo.findById(id);
        if (item.isEmpty()) {
            runException("Нет данных");
        }

        if (facultyRepo.existStudent(id)) {
            runException("Отклонение операции. Запись используется");
        }
        facultyRepo.deleteById(id);
        return item.orElseThrow(()-> {throw new ErrBadRequestException("Нет данных по идентификатору");});
    }
}
