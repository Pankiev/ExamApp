package pl.exam.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @GetMapping("/register")
    public String registerPage() {
        return "register sdaofjiasdif";
    }
}
