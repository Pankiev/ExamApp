package pl.exam.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController
{
	@Autowired
	private AuthentityChecker authentityChecker;

	@GetMapping("/register")
	public String registerPage()
	{
		if(authentityChecker.isUserLoggedIn())
			return "redirect:/";
		return "register/index";
	}
}
