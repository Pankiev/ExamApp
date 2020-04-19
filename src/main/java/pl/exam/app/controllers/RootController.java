package pl.exam.app.controllers;

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        method={RequestMethod.POST,RequestMethod.GET,}
)
public class RootController {
    @PostMapping("/")
    public String welcomePage(@ModelAttribute("asd") String x) {
        return "redirect:/denied/index";
    }

    private boolean isAdmin(SecurityContextHolderAwareRequestWrapper securityContext) {
        return securityContext.isUserInRole("admin");
    }

    private boolean isStudent(SecurityContextHolderAwareRequestWrapper securityContext) {
        return securityContext.isUserInRole("student");
    }

    private boolean isUserLoggedIn(SecurityContextHolderAwareRequestWrapper securityContext) {
        return securityContext.getRemoteUser() != null;
    }

} 
