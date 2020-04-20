package pl.exam.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
@RequestMapping("/users")
public class UsersController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "users/index";
    }
}
