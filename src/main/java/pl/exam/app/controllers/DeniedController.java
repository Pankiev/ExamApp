package pl.exam.app.controllers;

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeniedController
{
	@GetMapping({"/denied", "/denied/index"})
	public String deniedPage(SecurityContextHolderAwareRequestWrapper authentication)
	{
		if(authentication.isUserInRole("visitor"))
			return "denied/visitor";
		return "denied/index";
	}
}
