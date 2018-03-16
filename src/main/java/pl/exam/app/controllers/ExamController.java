package pl.exam.app.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.exam.app.database.entities.Exam;
import pl.exam.app.database.repositories.ExamRepository;

@Controller
@RequestMapping("/exam")
public class ExamController
{
	@Inject
	private ExamRepository examRepository; 
	
	@GetMapping({ "/", "/index" })
	public String examIndex()
	{
		return "exam/index";
	}

	@GetMapping("/create")
	public String examCreate()
	{
		return "exam/create";
	}
	
	@GetMapping("/{id}")
	public String examShow(@PathVariable("id") Integer examId, ModelMap model)
	{
		Exam exam = examRepository.findById(examId).get();
		model.addAttribute("exam", exam);
		return "exam/show";
	}
}
