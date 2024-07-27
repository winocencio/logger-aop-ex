package com.winocencio.example.controller;

import com.winocencio.config.logger.annotation.LoggerRootApiInvocation;
import com.winocencio.example.controller.dto.ExampleDto;
import com.winocencio.example.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@LoggerRootApiInvocation
public class ExampleController {

    private final ExampleService exampleService;

    @PostMapping("/api/v1/example")
    public ResponseEntity<ExampleDto> example(@RequestBody ExampleDto requestBody, @RequestHeader("teste") String teste) {

        try {
            var response = exampleService.transform(requestBody);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(requestBody);
        }
    }
}
