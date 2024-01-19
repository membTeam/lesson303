package usePostgres.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import java.nio.file.Files;

import java.io.*;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

import usePostgres.exception.ErrBadRequestException;
import usePostgres.models.Avatar;
import usePostgres.models.RecResponseData;
import usePostgres.repositories.AvatarRepository;
import usePostgres.repositories.StudentRepository;

@Service
public class AvatarService {
    private AvatarRepository avatarRepo;
    private StudentRepository studentRepo;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarService(AvatarRepository avatarRepo, StudentRepository studentRepo) {
        this.avatarRepo = avatarRepo;
        this.studentRepo = studentRepo;
    }

    private static String getExtensions(String filePath) {
        var lastIndex = filePath.lastIndexOf(".");
        return filePath.substring((lastIndex));
    }

    public void add(Long student_id, MultipartFile avatarFile) throws IOException {
        var student = studentRepo.getReferenceById(student_id);

        var fileName = student.getName() +
                getExtensions(avatarFile.getOriginalFilename());

        Path filePath = Path.of(avatarsDir,fileName);
        Files.deleteIfExists(filePath);

        try(InputStream is = avatarFile.getInputStream();
            OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024); ){

            bis.transferTo(bos);
        }

        Avatar avatar = new Avatar();

        avatar.setId(student_id);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize((int) avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());

        avatarRepo.save(avatar);
    }

    public void findAvatar(long id, HttpServletResponse response) {

        Avatar avatar = getAvatar(id);

        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();) {

            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        } catch (IOException ex) {
            throw new ErrBadRequestException("Нет данных");
        }
    }

    public RecResponseData downloadAvatar(long id) {
        var avatar = getAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return new RecResponseData(avatar.getData(), headers);
    }

    private Avatar getAvatar(Long id) {
        return avatarRepo
                .findById(id)
                .orElseThrow(()-> {throw new ErrBadRequestException("Нет данных");});
    }

}
