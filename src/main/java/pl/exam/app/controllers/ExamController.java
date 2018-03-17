package pl.exam.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.exam.app.database.entities.Exam;
import pl.exam.app.database.repositories.ExamRepository;

import java.util.Optional;

@Controller
@RequestMapping("/exam")
public class ExamController
{
	@Autowired
	private ExamRepository examRepository;

	@GetMapping({ "/", "/index" })
	public String examIndex(SecurityContextHolderAwareRequestWrapper authentication)
	{
		if(authentication.isUserInRole("admin"))
			return "exam/admin-index";
		if(authentication.isUserInRole("student"))
			return "exam/student-index";
		return "denied/index";
	}

	@GetMapping("/create")
	public String examCreate()
	{
		return "exam/create";
	}
	
	@GetMapping("/{id}")
	public String examShow(@PathVariable("id") Integer examId, ModelMap model)
	{
		Optional<Exam> exam = examRepository.findById(examId);
		if(!exam.isPresent())
			return "404/index";
		model.addAttribute("exam", exam.get());
		return "exam/show";
	}

	@GetMapping("/{id}/result")
	public String examResult(@PathVariable("id") Integer examId, ModelMap model)
	{
		Optional<Exam> exam = examRepository.findById(examId);
		if(!exam.isPresent())
			return "404/index";
		model.addAttribute("exam", exam.get());
		return "exam/result";
	}
}
