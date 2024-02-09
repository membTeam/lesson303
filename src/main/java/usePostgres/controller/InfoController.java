package usePostgres.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("port")
public class InfoController {

    @Value("${server.port}")
    private int port;

    @GetMapping
    public ResponseEntity<Integer> port() {
        return ResponseEntity.ok(port);
    }

}
