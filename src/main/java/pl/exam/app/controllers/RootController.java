package pl.exam.app.controllers;

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController
{
	@GetMapping("/")
	public String welcomePage(SecurityContextHolderAwareRequestWrapper securityContext)
	{
		if(!isUserLoggedIn(securityContext))
			return "redirect:/authentication/login";
		if(isAdmin(securityContext))
			return "admin-index";
		if(isStudent(securityContext))
			return "exam/student-index";

		return "denied/index";
	}
 
	private boolean isAdmin(SecurityContextHolderAwareRequestWrapper securityContext)
	{
		return securityContext.isUserInRole("admin");
	}

	private boolean isStudent(SecurityContextHolderAwareRequestWrapper securityContext)
	{
		return securityContext.isUserInRole("student");
	}

	private boolean isUserLoggedIn(SecurityContextHolderAwareRequestWrapper securityContext)
	{
		return securityContext.getRemoteUser() != null;
	}

} 
 