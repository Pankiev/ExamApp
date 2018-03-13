package pl.exam.app.examapp.jsf.beans.exam.event.lobby;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import org.springframework.stereotype.Component;

import pl.exam.app.examapp.database.entities.ExamEvent;
import pl.exam.app.examapp.database.repositories.ExamEventRepository;
import pl.exam.app.examapp.database.repositories.UserRepository;

@ManagedBean
@Component
@ApplicationScoped
public class OpenedEventLobby
{
	EventBus eventBus = EventBusFactory.getDefault().eventBus();
	
	@Inject
	private ExamEventRepository examEventRepository;
	
	@Inject
	private UserRepository userRepository;
	
	private Integer examEventId;
	
	private ExamEvent examEvent;
	
	private final Map<String, String> usersInLobby = new HashMap<>();
	
	public void addUser()
	{
		if(examEvent == null)
			examEvent = examEventRepository.findOne(examEventId);
		
		if(!userIsInRole("student"))
			return;
		
		String nickname = getNickname();
		usersInLobby.put(nickname, nickname);
		updateForm();	
	}
	
	
	public void start()
	{
		ExamEvent examEvent = examEventRepository.findOne(examEventId);
		examEvent.setOpened(false);
		examEvent.setStarted(true);
		examEvent.setStartDate(new Date());
		examEventRepository.save(examEvent);
		broadcast("/lobby_startt", "W Some message");
	}

	private boolean userIsInRole(String role)
	{
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role);
	}

	private String getNickname()
	{
		return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	}

	public Integer getExamEventId()
	{
		return examEventId;
	}

	public void setExamEventId(Integer examEventId)
	{
		this.examEventId = examEventId;
	}
	
	public Iterable<String> getUsers()
	{
		return usersInLobby.values();
	}
	
	public boolean isUserAlreadyInLobby()
	{
		return usersInLobby.containsKey(getNickname());
	}
	
	public void removeUser()
	{
		usersInLobby.remove(getNickname());
		updateForm();	
	}

	private void updateForm()
	{
		broadcast("/lobby", "Some message");
	}
	
	private void broadcast(String channel, String message)
	{
//		Broadcaster broadcaster = new SimpleBroadcaster();
//		
//		broadcaster.broadcast("");
	    eventBus.publish(channel, message);
	}
	
	public void messageHandled()
	{
		System.out.println("Message handled!");
	}
}
