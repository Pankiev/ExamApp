package pl.exam.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
@RequestMapping("/authentication/login")
public class LoginController
{
	@Autowired
	private AuthentityChecker authentityChecker;

	@GetMapping
	public String welcomePage()
	{
		if(authentityChecker.isUserLoggedIn())
			return "redirect:/";
		return "authentication/login";
	}
}
