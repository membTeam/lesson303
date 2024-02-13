package usePostgres.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import usePostgres.models.Avatar;
import usePostgres.service.AvatarService;

import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    AvatarService avatarService;
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("calculate-reduce")
    public ResponseEntity<Integer> calculateReduce() {
        return ResponseEntity.ok(avatarService.calculateReduce());
    }

    @GetMapping("page/{number}")
    public Page<Avatar> getPageAvatar(@PathVariable Integer number) {
        return avatarService.getPageAvatar(number);
    }


    @PostMapping(value = "/{student_id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> add(@PathVariable Long student_id,
                              @RequestParam MultipartFile avatar) throws IOException {
        avatarService.add(student_id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {

        var recData = avatarService.downloadAvatar(id);

        return ResponseEntity.status(HttpStatus.OK)
                .headers(recData.httpHeader())
                .body(recData.byteData());

    }

    @GetMapping("{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) {
        avatarService.findAvatar(id, response);
    }

}
