package usePostgres.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import usePostgres.models.Avatar;
import usePostgres.service.AvatarService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    AvatarService avatarService;
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }


    @PostMapping(value = "/{student_id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> add(@PathVariable Long student_id,
                              @RequestParam MultipartFile avatar) throws IOException {
        avatarService.add(student_id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        var avatar = avatarService.downloadAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar =  avatarService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

}
