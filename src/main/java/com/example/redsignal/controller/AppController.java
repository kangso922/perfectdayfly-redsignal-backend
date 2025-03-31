package com.example.perfectdayfly.redsignal.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AppController {

    @Operation(summary = "Hello World 테스트 메서드")
    @GetMapping
    public String hello() {
        return "Hello World!!";
    }

}
