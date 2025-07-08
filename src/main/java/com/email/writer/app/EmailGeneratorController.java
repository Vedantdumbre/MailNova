package com.email.writer.app;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/email")
public class EmailGeneratorController {

    public ResponseEntity<String> generateEmail(@RequestBody EmailRequest emailRequest) {
            return  ResponseEntity.ok("");
    }
}
