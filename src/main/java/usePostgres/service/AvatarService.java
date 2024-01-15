package usePostgres.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import usePostgres.exception.ErrBadRequestException;
import usePostgres.models.Avatar;
import usePostgres.repositories.AvatarRepository;
import usePostgres.repositories.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {
    private AvatarRepository avatarRepo;
    private StudentRepository studentRepo;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private static String getExtensions(String filePath) {
        var lastIndex = filePath.lastIndexOf(".");
        return filePath.substring((lastIndex));
    }

    public AvatarService(AvatarRepository avatarRepo, StudentRepository studentRepo) {
        this.avatarRepo = avatarRepo;
        this.studentRepo = studentRepo;
    }

    public void add(Long student_id, MultipartFile avatarFile) throws IOException {
        var student = studentRepo.getReferenceById(student_id);

        Path filePath = Path.of(avatarsDir,
                student.getName() +
                         getExtensions(avatarFile.getOriginalFilename()));

        Files.deleteIfExists(filePath);

        try(InputStream is = avatarFile.getInputStream();
            OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024); ){

            bis.transferTo(bos);
        }

        Avatar avatar = new Avatar();

        //avatar.setId(student_id);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize((int) avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());

        avatarRepo.save(avatar);

    }

    public Avatar downloadAvatar(Long id) {
        return avatarRepo
                .findById(id)
                .orElseThrow(()-> {throw new ErrBadRequestException("Нет данных");});
    }


    public Avatar findAvatar(Long id) {
        return avatarRepo.findById(id).orElseThrow(()-> {throw new ErrBadRequestException("Нет данных");});
    }

}
