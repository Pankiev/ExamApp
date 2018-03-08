package pl.exam.app.examapp.controllers;

import javax.inject.Inject;

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.exam.app.examapp.database.entities.ExamEvent;
import pl.exam.app.examapp.database.repositories.ExamEventRepository;

@Controller
@RequestMapping("/exam-event")
public class ExamEventController
{
	@Inject
	private ExamEventRepository examEventRepository;
	
	@GetMapping({"", "/", "/index"})
	public String index(SecurityContextHolderAwareRequestWrapper authentication)
	{
		if(authentication.isUserInRole("admin"))
			return "exam-event/index-admin";
		return "exam-event/index-student";
	}
	
	@GetMapping("/create")
	public String create()
	{
		return "exam-event/create";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer id, ModelMap model, SecurityContextHolderAwareRequestWrapper authentication)
	{
		ExamEvent examEvent = examEventRepository.findOne(id);
		model.addAttribute("examEvent", examEvent);
		if(authentication.isUserInRole("admin"))
			return pathForAdmin(examEvent);
		return pathForStudent(examEvent);
	}

	private String pathForAdmin(ExamEvent examEvent)
	{
		if(!examEvent.getStarted())
			if(!examEvent.getOpened())
				return "exam-event/not-started";
			else
				return "exam-event/opened-admin";
		
		if(examEventInProgress(examEvent))
				return "exam-event/in-progress-admin";
		return "exam-event/ended";
	}
	
	private String pathForStudent(ExamEvent examEvent)
	{
		if(examEvent.getOpened())
				return "exam-event/opened-student";
		
		if(examEventInProgress(examEvent))
			return "exam-event/in-progress-student";
		if(examEvent.getEnded())
			return "exam-event/ended";
		return "redirect:/denied/index";
	}


	private boolean examEventInProgress(ExamEvent examEvent)
	{
		return examEvent.getStarted() && !examEvent.getEnded();
	}
}
