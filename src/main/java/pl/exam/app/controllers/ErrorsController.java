package pl.exam.app.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorsController implements ErrorController {

    @GetMapping
    public String errorView() {
        return "404/index";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
