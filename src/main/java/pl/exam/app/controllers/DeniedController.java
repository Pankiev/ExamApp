package pl.exam.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeniedController
{
	@GetMapping("denied/index")
	public String deniedPage()
	{
		return "denied/index";
	}
}
