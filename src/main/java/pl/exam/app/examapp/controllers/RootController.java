package pl.exam.app.examapp.controllers;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController
{
	@GetMapping("/")
	public String welcomePage(ServletRequest sr)
	{
		return "index";
	}
} 
